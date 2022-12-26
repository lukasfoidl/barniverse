<template>
    <div>
        <EmailInput :trigger="trigger" />
        <PasswordInput :trigger="trigger" />

        <div class="pt-1 mb-4">
            <button class="btn btn-primary" type="button" v-on:click="getValues">Login</button>
        </div>

        <div>
            Don't have an account? <router-link id="register" class="link" to="/register">Register here</router-link>
        </div>
    </div>
</template>

<script>
import http from "../../http"
import EmailInput from "../molecules/EmailInput.vue"
import PasswordInput from "../molecules/PasswordInput.vue";

export default {
    name: "LoginForm",
    data: () => ({
        values: {
            email: "",
            password: "",
        },
        validationResults: [],
        trigger: false
    }),
    mounted() {
        window.event.on("validationSuccessful", async (data) => {
            this.checkValidationResults(data);
        })
    },
    unmounted() {
        window.event.all.delete("validationSuccessful");
    },
    methods: {
        // Login Button
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
                    console.log("LOGIN")
                    await this.authenticate();
                } catch (error) {
                    const modalData = {
                        title: "Error (" + error.response.status + ")",
                        text: error.response.data
                    }
                    window.event.emit("showErrorModal", modalData);
                }
            }
        },
        async authenticate() {
            try {
                const response = await http.post("/login", this.values)
                sessionStorage.setItem("jwt-token", response.data["jwt-token"]);
                window.router.push("/");
                window.event.emit("reloadJWT");
            } catch (error) {
                console.log(error);
                const data = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                };
                window.event.emit("showErrorModal", data);
            }
        }
    },
    components: { EmailInput, PasswordInput }
}

</script>

<style>
</style>