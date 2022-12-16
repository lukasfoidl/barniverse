<template>
    <div>
            <!--Email-->
            <div>
                <label class="form-label" for="form2Example18">Email </label>
                <input type="email" id="email" class="form-control" v-model="values.email" @blur="validate('email')" />
                <div class="" id="feedback-email">
                    <p class="errorMessage">{{ errors.email }}&nbsp;</p>
                </div>
            </div>
            <!--Password-->
            <div>
                <label class="form-label" for="form2Example28">Password</label>
                <input type="password" id="password" class="form-control" v-model="values.password" @blur="validate('password')" />
                <div class="" id="feedback-password">
                    <p class="errorMessage">{{ errors.password }}&nbsp;</p>
                </div>
            </div>
            
            <div class="pt-1 mb-4">
                <button class="btn btn-primary" type="button" v-on:click.prevent="login">Login</button>
            </div>

            <div>
                Don't have an account? <router-link id="register" class="link" to="/register">Register here</router-link>
            </div>
    </div>
</template>

<script>
import { object, string } from "yup"
import http from "../../http"

export default {
    name: "LoginForm",
    data: () => ({
        values: {
            email: "",
            password: "",
        },
        errors: {
            email: "",
            password: "",
        }
    }),
    methods: {
        async login() {
            loginFormSchema
                .validate(this.values, { abortEarly: false })
                .then(async () => {
                    try {
                        console.log("LOGIN")
                        await this.authenticate();
                    } catch (error) {
                        console.error(error)
                    }
                })
                .catch((errors) => {
                    errors.inner.forEach(element => {
                        this.errors[element.path] = element.message
                        window.$("#" + element.path).removeClass("is-valid");
                        window.$("#" + element.path).addClass("is-invalid");
                        window.$("#feedback-" + element.path).removeClass("valid-feedback");
                        window.$("#feedback-" + element.path).addClass("invalid-feedback");
                    })
                })
        },
        async authenticate() {
            const data = {
                email: this.values.email,
                password: this.values.password
            };
            http.post("/login", data)
                .then(function (response) {
                    sessionStorage.setItem("jwt-token", response.data['jwt-token']);
                    window.router.push('/')
                    window.event.emit("reloadJWT");
                })
                .catch(function (error) {
                    console.log(error)
                    const data = {
                        title: "Error (" + error.response.status + ")",
                        text: error.response.data
                    }
                    window.event.emit("showModal", data);
                });
        },
        validate(field) {
            loginFormSchema
                .validateAt(field, this.values)
                .then(() => {
                    this.errors[field] = ""
                    window.$("#" + field).removeClass("is-invalid");
                    window.$("#" + field).addClass("is-valid");
                    window.$("#feedback-" + field).removeClass("invalid-feedback");
                    window.$("#feedback-" + field).addClass("valid-feedback");
                })
                .catch((error) => {
                    this.errors[field] = error.message
                    window.$("#" + field).removeClass("is-valid");
                    window.$("#" + field).addClass("is-invalid");
                    window.$("#feedback-" + field).removeClass("valid-feedback");
                    window.$("#feedback-" + field).addClass("invalid-feedback");
                })
                
        }

    }

}

const loginFormSchema = object().shape({
    email: string().email("Email must be valid!").required("Email is required!"),
    password: string().min(8, "Password must be at least 8 Characters!").required("Password is required!"),
})
</script>

<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
.invalid-feedback, .valid-feedback {
    margin-top: 0 !important;
}
</style>