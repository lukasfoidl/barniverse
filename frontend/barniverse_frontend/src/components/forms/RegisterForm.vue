<template>
    <div class="row">
        <div class="col-md-6">
            <FirstNameInput :trigger="trigger"/>
        </div>
        <div class="col-md-6">
            <LastNameInput :trigger="trigger" />
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <EmailInput :trigger="trigger"/>
        </div>
        <div class="col-md-6">
            <UsernameInput :trigger="trigger" />
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <PasswordInput :trigger="trigger" />
        </div>
        <div class="col-md-6">
            <ConfirmationPasswordInput :trigger="trigger" />
        </div>
    </div>
    <!-- <div class="row">
        <ProfilePictureInput :trigger="trigger" />
    </div> -->

    <!-- <div class="row">
        <div class="col-md-12">
            <div class="form-outline">
                <label class="form-label" for="emailAddress">Profile Picture</label>
                <input type="file" class="form-control" v-on:change="values.profilePicture"
                    placeholder="Photo" capture @blur="validate('profilePicture')" />
                <div class="" id="feedback-confirmationPassword">
                    <p class="errorMessage">{{ errors.profilePicture }}&nbsp;</p>
                </div>
            </div>
        </div>
    </div> -->

    <div class="mt-4">
        <input class="btn btn-primary" type="submit" v-on:click.prevent="getValues" value="Register" />
    </div>
    <div class="mt-4">
        <router-link id="login" class="link" to="/login">
            already have an account?
        </router-link>
    </div>
</template>

<script>
import http from "../../http"
import FirstNameInput from "../molecules/FirstNameInput.vue"
import LastNameInput from "../molecules/LastNameInput.vue"
import EmailInput from "../molecules/EmailInput.vue"
import UsernameInput from "../molecules/UsernameInput.vue"
import ConfirmationPasswordInput from "../molecules/ConfirmationPasswordInput.vue"
import PasswordInput from "../molecules/PasswordInput.vue"

export default {
    name: "RegisterForm",
    data: () => ({
        values: {
            firstname: "",
            lastname: "",
            username: "",
            email: "",
            password: "",
            confirmationPassword: "",
            // profilePicture: ""
        },
        errors: {},
        validationResults: [],
        trigger: false
    }),
    components: { FirstNameInput, LastNameInput, EmailInput, UsernameInput, ConfirmationPasswordInput, PasswordInput },
    mounted() {
        window.event.on("validationSuccessful", async (data) => {
            this.checkValidationResults(data);
        })
    },
    unmounted() {
        window.event.all.delete("validationSuccessful");
    },
    methods: {
        // Register Button
        getValues() {
            this.validationResults = []
            this.trigger = !this.trigger // change of trigger triggers validation events of children
        },
        async checkValidationResults(data) {
            // save validation results
            this.validationResults.push(data.field);
            this.values[data.field] = data.value;

            // only if all results received
            if (this.validationResults.length === Object.keys(this.values).length) {
                // check if all values have been successfully validated and added to validationResults
                for (var key in this.values) {
                    var foundKey = false
                    for (var index in this.validationResults) {
                        if (this.validationResults[index] === key) {
                            foundKey = true;
                            break;
                        }
                    }
                    if (!foundKey) {
                        return;
                    }
                }
                try {
                    console.log("REGISTER")
                    await this.saveUser();
                } catch (error) {
                    const modalData = {
                        title: "Error (" + error.response.status + ")",
                        text: error.response.data
                    }
                    window.event.emit("showErrorModal", modalData);
                }
            }
        },
        async saveUser() {
            try {
                const data = {
                    firstname: this.values.firstname,
                    lastname: this.values.lastname,
                    email: this.values.email,
                    username: this.values.username,
                    password: this.values.password,
                    // picture: this.values.picture
                }
                const response = await http.post("/register", data)
                sessionStorage.setItem("jwt-token", response.data['jwt-token']);
                window.event.emit("reloadJWT");
                window.router.push('/')
                const modalData = {
                    title: "Welcome in the Barniverse!",
                    text: "User created successfully!"
                }
                window.event.emit("showErrorModal", modalData);
            } catch (error) {
                console.log(error)
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
        },
    }
}
    // profilePicture: string()
</script>

<style>
#formRegister {
    width: 40em;
}

.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}

.invalid-feedback,
.valid-feedback {
    margin-top: 0 !important;
}
</style>