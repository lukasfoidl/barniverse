<template>
    <div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-outline">
                    <!-- Firstname-->
                    <label class="form-label" for="firstName">First Name</label>
                    <input type="text" class="form-control form-control-lg" v-model="values.fname" id="fname"
                        @blur="validate('fname')" />
                    <div class="" id="feedback-fname">
                        <p class="errorMessage">{{ errors.fname }}&nbsp;</p>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-outline">
                    <!-- Lastname-->
                    <label class="form-label" for="lastName">Last Name</label>
                    <input type="text" class="form-control form-control-lg" v-model="values.lname" id="lname"
                        @blur="validate('lname')" />
                    <div class="" id="feedback-lname">
                        <p class="errorMessage">{{ errors.lname }}&nbsp;</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="form-outline">
                    <!-- Email-->
                    <label class="form-label" for="emailAddress">Email</label>
                    <input type="email" id="emailR" class="form-control form-control-lg" v-model="values.emailR"
                        aria-describedby="emailHelp" @blur="validate('emailR')" />
                    <div class="" id="feedback-emailR">
                        <p class="errorMessage">{{ errors.emailR }}&nbsp;</p>
                    </div>
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-outline ">
                    <!-- Username-->
                    <label class="form-label" for="Username">Username</label>
                    <input class="form-control form-control-lg" type="text" v-model="values.username" id="username"
                        @blur="validate('username')" />
                    <div class="" id="feedback-username">
                        <p class="errorMessage">{{ errors.username }}&nbsp;</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="form-outline">
                    <!-- Password-->
                    <label class="form-label" for="passwordRegister">Password</label>
                    <input type="password" id="passwordR" class="form-control form-control-lg"
                        v-model="values.passwordR" @blur="validate('passwordR')" />
                    <div class="" id="feedback-passwordR">
                        <p class="errorMessage">{{ errors.passwordR }}&nbsp;</p>
                    </div>
                </div>
            </div>

            <div class="col-md-6">
                <div class="form-outline">
                    <!-- Confirm Password-->
                    <label class="form-label" for="confirmPassword">Confirm Password</label>
                    <input type="password" id="confirmPassword" class="form-control form-control-lg"
                        v-model="values.confirmPassword" @blur="validate('confirmPassword')" />
                    <div class="" id="feedback-confirmPassword">
                        <p class="errorMessage">{{ errors.confirmPassword }}&nbsp;</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="form-outline ">
                    <label for="birthdayDate" class="form-label">Birthday</label>
                    <input type="date" class="form-control form-control-lg" id="dateOfBirth"
                        v-model="values.dateOfBirth" @blur="validate('dateOfBirth')" />
                    <div class="" id="feedback-dateOfBirth">
                        <p class="errorMessage">{{ errors.dateOfBirth }}&nbsp;</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <div class="form-outline">
                    <label class="form-label" for="emailAddress">Profile Picture</label>
                    <input type="file" class="form-control form-control-lg" v-on:change="values.profilePicture"
                        placeholder="Photo" capture @blur="validate('profilePicture')" />
                    <div class="" id="feedback-confirmPassword">
                        <p class="errorMessage">{{ errors.profilePicture }}&nbsp;</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="mt-4">
            <input class="btn btn-primary btn-lg" type="submit" v-on:click.prevent="register" value="Register" />
        </div>
        <div class="mt-4">
            <router-link id="login" class="link" to="/login">
                already have an account?
            </router-link>
        </div>
    </div>
</template>

<script>
import { date, object, string } from "yup"
import http from "../../http"

export default {
    name: "RegisterForm",
    data: () => ({
        values: {

            fname: "",
            lname: "",
            username: "",
            emailR: "",
            passwordR: "",
            confirmPassword: "",
            dateOfBirth: null, // need empty date obj
            profilePicture: ""

        },
        errors: {}
    }),
    methods: {
        //Register Button
        async register() {
            registerFormSchema
                .validate(this.values, { abortEarly: false })
                .then(async () => {
                    try {
                        console.log("REGISTER")
                        await this.saveUser();
                    } catch (error) {
                        console.error(error)
                    }
                })
                .catch((errors) => {
                    errors.inner.forEach(element => {
                        console.log(element.path)
                        this.errors[element.path] = element.message
                        window.$("#" + element.path).removeClass("is-valid");
                        window.$("#" + element.path).addClass("is-invalid");
                        window.$("#feedback-" + element.path).removeClass("valid-feedback");
                        window.$("#feedback-" + element.path).addClass("invalid-feedback");
                    })
                })
        },
        async saveUser() {
            const data = {
                firstname: this.values.fname,
                lastname: this.values.lname,
                username: this.values.username,
                email: this.values.emailR,
                password: this.values.passwordR,
                picture: this.values.profilePicture,
                birthday: this.values.dateOfBirth,
            };
            http.post("/register", data)
                .then(function (response) {
                    sessionStorage.setItem("jwt-token", response.data['jwt-token']);
                    window.event.emit("reloadJWT");
                    window.router.push('/')
                    const data = {
                        title: "Welcome in the Barniverse!",
                        text: "User created successfully!"
                    }
                    window.event.emit("showModal", data);
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
            registerFormSchema
                .validateAt(field, this.values)
                .then(() => {
                    this.errors[field] = "Looks Good."
                    console.log(field);
                    window.$("#" + field).removeClass("is-invalid");
                    window.$("#" + field).addClass("is-valid");
                    window.$("#feedback-" + field).removeClass("invalid-feedback");
                    window.$("#feedback-" + field).addClass("valid-feedback");
                })
                .catch((error) => {
                    this.errors[field] = error.message
                    console.log(field);
                    window.$("#" + field).removeClass("is-valid");
                    window.$("#" + field).addClass("is-invalid");
                    window.$("#feedback-" + field).removeClass("valid-feedback");
                    window.$("#feedback-" + field).addClass("invalid-feedback");
                })
        }
    }
}

//validate here
const registerFormSchema = object().shape({
    fname: string().required("First Name is required!"),
    lname: string().required("Last Name is reuired!"),
    username: string().min(5, "Username must be between 5 and 20 Characters long!").max(20, "Username must be between 5 and 20 Characters long!").required("Username is required!"),
    emailR: string().email("Email must be valid!").required("Email is required!"),
    passwordR: string().min(8, "Password must be at least 8 Characters!").required("Password is required!"),
    confirmPassword: string().min(8, "Password must be at least 8 Characters!").required("Confirm Password is required!").test('passwords-match', 'Passwords must match', function (value) {
        return this.parent.passwordR === value
    }),
    dateOfBirth: date(),
    profilePicture: string()
})
</script>

<style>
#formRegister {
    width: 40em;
}

.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}

.invalid-feedback, .valid-feedback {
    margin-top: 0 !important;
}
</style>