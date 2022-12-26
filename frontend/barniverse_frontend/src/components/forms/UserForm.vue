<template>
    <!-- <div class="row">
        <div class="centerPictureDiv mb-3">
            <div class="d-flex flex-column align-items-center text-center">
                <img class="rounded-circle userImg" id="img" :src="values.picture">
                <div class="col-md-12 mt-2">
                    <input class="mt-2 form-control" id="file" type="file" @change="onChangePicture">
                </div>
            </div>
        </div>
    </div> -->

    <div class="row">
        <div class="col-md-6">
            <FirstNameInput :trigger="trigger" :firstname="this.user.firstname" />
        </div>
        <div class="col-md-6">
            <LastNameInput :trigger="trigger" :lastname="this.user.lastname" />
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <EmailInput :trigger="trigger" :email="this.user.email" />
        </div>
        <div class="col-md-6">
            <UsernameInput :trigger="trigger" :username="this.user.username" />
        </div>
    </div>
            
    <div class="text-center">
        <button class="btn btn-primary buttonSpacer" @click="getValues">
            Save Changes
        </button>
        <button class="btn btn-secondary buttonSpacer" @click="changePassword">
            Change password
        </button>
        <button class="btn btn-danger buttonSpacer" @click="getPermission">
            Delete Profile
        </button>
    </div>

    <ChangePasswordModal :id="this.user.id" />
</template>

<script>
import http from "@/http"
import FirstNameInput from "../molecules/FirstNameInput.vue"
import LastNameInput from "../molecules/LastNameInput.vue"
import EmailInput from "../molecules/EmailInput.vue"
import UsernameInput from "../molecules/UsernameInput.vue"
import ChangePasswordModal from "../molecules/ChangePasswordModal.vue"

export default {
    name: "UserForm",
    props: ["user"],
    components: { FirstNameInput, LastNameInput, EmailInput, UsernameInput, ChangePasswordModal },
    data: () => ({
        values: [
            "firstname",
            "lastname",
            "username",
            "email",
            // profilePicture: ""
        ],
        errors: {},
        validationResults: {},
        trigger: false
    }),
    mounted() {
        window.event.on("validationSuccessful", async (data) => {
            this.checkValidationResults(data);
        })

        window.event.on("permissionGranted_deactivateProfile", () => {
            this.deactivateProfile()
        });
    },
    unmounted() {
        window.event.all.delete("permissionGranted_deactivateProfile");
        window.event.all.delete("validationSuccessful");
    },
    methods: {
        // Save Changes Button
        getValues() {
            this.validationResults = {}
            this.trigger = !this.trigger // change of trigger triggers validation events of children
        },
        async checkValidationResults(data) {
            // save validation results
            this.validationResults[data.field] = data.value;

            // only if all results received
            if (Object.keys(this.validationResults).length === this.values.length) {
                // check if all values have been successfully validated and added to validationResults
                for (var index in this.values) {
                    var foundKey = false
                    for (var key in this.validationResults) {
                        if (this.values[index] === key) {
                            foundKey = true;
                            break;
                        }
                    }
                    if (!foundKey) {
                        return;
                    }
                }
                try {
                    console.log("UPDATE")
                    await this.updateUser();
                } catch (error) {
                    const modalData = {
                        title: "Error (" + error.response.status + ")",
                        text: error.response.data
                    }
                    window.event.emit("showErrorModal", modalData);
                }
            }
        },
        async updateUser() {
            try {
                const data = {
                    id: this.user.id,
                    firstname: this.validationResults.firstname,
                    lastname: this.validationResults.lastname,
                    username: this.validationResults.username,
                    email: this.validationResults.email,
                    // picture: this.values.picture,
                    picture: "",
                    state: this.user.state
                }
                const response = await http.put("users", data, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                sessionStorage.setItem("jwt-token", response.data["jwt-token"]);
                window.event.emit("reloadJWT");
                const modalData = {
                    title: "Info (" + response.status + ")",
                    text: "User updated successfully!"
                }
                window.event.emit("showErrorModal", modalData);
            } catch(error) {
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
        },
        onChangePicture(e) {
            this.values.picture = e.target.files[0];
        },
        // Delete Profile Button
        getPermission() {
            var modalData = {
                title: "Warning",
                text: "Do you really want to delete user " + window.username + "?",
                id: "deactivateProfile"
            }
            window.event.emit("showPermissionModal", modalData)
        },
        async deactivateProfile() {
            try {
                const response = await http.put("users/" + window.uuid, null, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                sessionStorage.removeItem("jwt-token");
                window.event.emit("reloadJWT");
                window.router.push("/")
                const modalData = {
                    title: "Info (" + response.status + ")",
                    text: "User deleted successfully!" // deleted and not deactivated because of "Delete"-Button
                }
                window.event.emit("showErrorModal", modalData);
            } catch (error) {
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
        },
        changePassword() {
            window.event.emit("showChangePasswordModal")
        }
    }
}
</script>

<style>
.userImg {
    width: 150px;
    height: 150px;
    border-style: solid;
    border-color: #ebdbc7;
}

.fullName {
    font-size: 20px;
}

.usercard {
    max-width: 100%;
    background-color: #ebdbc7;
}

.buttonSpacer {
   margin: 5px;
}

.centerPictureDiv {
    display: flex;
    justify-content: center;
    flex-direction: column;
}
</style>