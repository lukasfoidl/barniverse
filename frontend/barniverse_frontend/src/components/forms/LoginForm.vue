<template>
    <div>

        <form id="formLogin">

            <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Log in</h3>

            <div class="form-outline mb-4">
                <label class="form-label" for="form2Example18">Email </label>
                <input type="email" id="email" class="form-control form-control-lg" v-model="values.email"
                    @blur="validate('email')" />
            </div>

            <div class="form-outline mb-4">
                <label class="form-label" for="form2Example28">Password</label>
                <input type="password" id="password" class="form-control form-control-lg" v-model="values.password"
                    @blur="validate('password')" />
            </div>
            <div>
                <p v-if="!!errors.email" class="text-danger">
                    {{ errors.email }}
                </p>
                <p v-if="!!errors.password" class="text-danger">
                    {{ errors.password }}
                </p>

            </div>
            <div class="pt-1 mb-4">
                <button class="btn btn-primary btn-lg" type="button" v-on:click.prevent="login">Login</button>
            </div>


            <p>Don't have an account? <router-link id="register" class="link" to="/register">Register here</router-link>
            </p>

        </form>




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
                .validate(this.values, { abortEarly: true })
                .then(async () => {
                    this.errors = {
                        email: "",
                        password: "",
                    }
                    try {
                        console.log("LOGIN")
                        await this.authenticate();
                    } catch (error) {
                        console.error(error)
                    }
                })
                .catch((error) => {
                    error.inner.forEach(() => {
                        this.errors[error.path] = error.message
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
                })
                .catch((error) => {
                    this.errors[field] = error.message
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
#formLogin {
    padding: 1cm;
    background-color: #ebdbc7;
    border-radius: 25px;
}
</style>