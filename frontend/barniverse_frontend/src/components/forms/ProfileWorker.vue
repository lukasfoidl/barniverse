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

    <UserForm :key="this.user.id" :trigger="this.trigger" :user="this.user" />
            
    <div class="text-center">
        <button class="btn btn-primary buttonSpacer" @click="triggerValidation">
            Save Changes
        </button>
        <button class="btn btn-secondary buttonSpacer" @click="changePassword">
            Change password
        </button>
        <button class="btn btn-danger buttonSpacer" @click="getPermission">
            Delete Profile
        </button>
    </div>
</template>

<script>
import http from "@/http"
import UserForm from "@/components/forms/UserForm.vue"

export default {
    name: "ProfileWorker",
    props: ["user"],
    components: { UserForm },
    data: () => ({
        trigger: false
    }),
    mounted() {
        window.event.on("permissionGranted_deleteUser", () => {
            this.deleteUser()
        });
        window.event.on("validationCompleted", (data) => {
            this.updateUser(data)
        });
    },
    unmounted() {
        window.event.all.delete("permissionGranted_deleteUser");
        window.event.all.delete("validationCompleted");
    },
    methods: {
        // Save Changes Button
        triggerValidation() {
            this.trigger = !this.trigger // change of trigger triggers validation events of children
        },
        async updateUser(data) {
            try {
                const requestBody = {
                    id: data.id,
                    firstname: data.firstname,
                    lastname: data.lastname,
                    email: data.email,
                    username: data.username,
                }
                const response = await http.put("users", requestBody, {
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
                text: "Do you really want to delete " + this.$store.state.username + "?",
                id: this.user.id,
                type: "deleteUser"
            }
            window.event.emit("showPermissionModal", modalData)
        },
        async deleteUser() {
            try {
                const response = await http.put("users/deleteWithState/" + this.$store.state.uuid, null, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                sessionStorage.removeItem("jwt-token");
                window.event.emit("reloadJWT");
                this.$router.push("/")
                const modalData = {
                    title: "Info (" + response.status + ")",
                    text: "User " + this.$store.state.username + " deleted successfully!"
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
            this.$router.push("users/changePassword")
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