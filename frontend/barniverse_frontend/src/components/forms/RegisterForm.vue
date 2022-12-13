<template>
    <div>
        <div class="row">
            <div class="col-md-6 mb-4">
                <div class="form-outline">
                    <label class="form-label" for="firstName">First Name</label>
                    <input type="text" class="form-control form-control-lg" v-model="values.fname" id="fname"
                        @blur="validate('fname')" />
                </div>

            </div>
            <div class="col-md-6 mb-4">
                <div class="form-outline">
                    <label class="form-label" for="lastName">Last Name</label>
                    <input type="text" class="form-control form-control-lg" v-model="values.lname" id="lname"
                        @blur="validate('lname')" />
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-4">
                <div class="form-outline">
                    <label class="form-label" for="emailAddress">Email</label>
                    <input type="email" id="emailAddress" class="form-control form-control-lg"
                        v-model="values.emailR" aria-describedby="emailHelp" @blur="validate('emailR')" />
                </div>
            </div>
            <div class="col-md-6 mb-4 ">
                <div class="form-outline ">
                    <label class="form-label" for="Username">Username</label>
                    <input class="form-control form-control-lg" type="text" v-model="values.username" id="username"
                        @blur="validate('username')" />
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-4 pb-2">
                <div class="form-outline">
                    <label class="form-label" for="passwordRegister">Password</label>
                    <input type="password" id="passwordRegister" class="form-control form-control-lg"
                        v-model="values.passwordR" @blur="validate('passwordR')" />
                </div>
            </div>
            <div class="col-md-6 mb-4 pb-2">
                <div class="form-outline">
                    <label class="form-label" for="confirmPassword">Confirm Password</label>
                    <input type="password" id="confirmPassword" class="form-control form-control-lg"
                        v-model="values.confirmPassword" @blur="validate('confirmPassword')" />
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-4 pb-2">
                <div class="form-outline ">
                    <label for="birthdayDate" class="form-label">Birthday</label>
                    <input type="date" class="form-control form-control-lg" id="birthdayDate"
                        v-model="values.dateOfBirth" @blur="validate('dateOfBirth')" />
                </div>

            </div>
            <div class="col-md-6 mb-4 pb-2">
                <div class="form-outline">
                    <label class="form-label" for="phoneNumber">Phone Number</label>
                    <input type="tel" id="phoneNumber" class="form-control form-control-lg" />
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 mb-4 pb-2">
                <div class="form-outline">
                    <label class="form-label" for="emailAddress">Profile Picture</label>
                    <input type="file" class="form-control form-control-lg" v-on:change="values.profilePicture"
                        placeholder="Photo" s capture @blur="validate('profilePicture')" />
                </div>
            </div>
        </div>

        <div>
            <p v-if="!!errors.fname" class="text-danger">
                {{ errors.fname }}
            </p>
            <p v-if="!!errors.lname" class="text-danger">
                {{ errors.lname }}
            </p>
            <p v-if="!!errors.username" class="text-danger">
                {{ errors.username }}
            </p>
            <p v-if="!!errors.emailR" class="text-danger">
                {{ errors.emailR }}
            </p>
            <p v-if="!!errors.passwordR" class="text-danger">
                {{ errors.passwordR }}
            </p>
            <p v-if="!!errors.confirmPassword" class="text-danger">
                {{ errors.confirmPassword }}
            </p>
            <p v-if="!!errors.dateOfBirth" class="text-danger">
                {{ errors.dateOfBirth }}
            </p>
            <p v-if="!!errors.profilePicture" class="text-danger">
                {{ errors.profilePicture }}
            </p>
        </div>

        <div class="mt-4 pt-2">
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
                .validate(this.values, { abortEarly: true })
                .then(async () => {
                    try {
                        console.log("REGISTER")
                        await this.saveUser();
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
                    this.errors[field] = ""
                })
                .catch((error) => {
                    this.errors[field] = error.message
                })
        }
    }
}

//validate here
const registerFormSchema = object().shape({
    fname: string().required("First Name is required!"),
    lname: string().required("Last Name is reuired!"),
    username: string().min(8, "Username must be at least 8 Characters!").required("Username is required!"),
    emailR: string().email("Email must be valid!").required("Email is required!"),
    passwordR: string().min(8, "Password must be at least 8 Characters!").required("Password is required!"),
    confirmPassword: string().min(8, "Confirm Password must be at least 8 Characters!").required("Confirm Password is required!").test('passwords-match', 'Passwords must match', function (value) {
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
</style>