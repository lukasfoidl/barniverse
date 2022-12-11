<template>

    <div id="ImageRegister">
        <div class="row">
            <div class="col-md-6 mb-4">
                <div class="form-outline">
                    <!-- Firstname-->
                    <label class="form-label" for="firstName">First Name</label>
                    <input type="text" class="form-control form-control-lg" v-model="form.values.fname" id="fname" @blur="validate('fname')" />
                    <div class="" id= "feedback-fname">
                         <p class ="errorMessage">{{ form.errors.fname }}&nbsp;</p>
                    </div>
                </div>
            </div>

            <div class="col-md-6 mb-4">
                <div class="form-outline">
                    <!-- Lastname-->
                    <label class="form-label" for="lastName">Last Name</label>
                    <input type="text" class="form-control form-control-lg" v-model="form.values.lname" id="lname"
                        @blur="validate('lname')" />
                        <div class="" id= "feedback-lname">
                            <p class ="errorMessage">{{ form.errors.lname }}&nbsp;</p>
                        </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-4">
                <div class="form-outline">
                    <!-- Email-->
                    <label class="form-label" for="emailAddress">Email</label>
                    <input type="email" id="emailR" class="form-control form-control-lg" v-model="form.values.emailR" aria-describedby="emailHelp" @blur="validate('emailR')" />
                    <div class="" id= "feedback-emailR">
                            <p class ="errorMessage">{{ form.errors.emailR }}&nbsp;</p>
                        </div>
                </div>
            </div>

            <div class="col-md-6 mb-4 ">

                <div class="form-outline ">
                    <!-- Username-->
                    <label class="form-label" for="Username">Username</label>
                    <input class="form-control form-control-lg" type="text" v-model="form.values.username" id="username" @blur="validate('username')" />
                    <div class="" id= "feedback-username">
                        <p class ="errorMessage">{{ form.errors.username }}&nbsp;</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-4 pb-2">
                <div class="form-outline">
                    <!-- Password-->
                    <label class="form-label" for="passwordRegister">Password</label>
                    <input type="password" id="passwordR" class="form-control form-control-lg" v-model="form.values.passwordR" @blur="validate('passwordR')"  />
                    <div class="" id= "feedback-passwordR">
                        <p class ="errorMessage">{{ form.errors.passwordR }}&nbsp;</p>
                    </div>
                </div>
            </div>

            <div class="col-md-6 mb-4 pb-2">
                <div class="form-outline">
                    <!-- Confirm Password-->
                    <label class="form-label" for="confirmPassword">Confirm Password</label>
                    <input type="password" id="confirmPassword" class="form-control form-control-lg" v-model="form.values.confirmPassword" @blur="validate('confirmPassword')" />
                    <div class="" id= "feedback-confirmPassword">
                        <p class ="errorMessage">{{ form.errors.confirmPassword }}&nbsp;</p>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-4 pb-2">
                <div class="form-outline ">
                    <label for="birthdayDate" class="form-label">Birthday</label>
                    <input type="date" class="form-control form-control-lg" id="dateOfBirth" v-model="form.values.dateOfBirth" @blur="validate('dateOfBirth')"  />
                    <div class="" id= "feedback-dateOfBirth">
                        <p class ="errorMessage">{{ form.errors.dateOfBirth }}&nbsp;</p>
                    </div>
                </div>
            </div>
        <!-- Phone Number not needed-->
        <!--<div class="col-md-6 mb-4 pb-2">
                <div class="form-outline">
                    <label class="form-label" for="phoneNumber">Phone Number</label>
                    <input type="tel" id="phoneNumber" class="form-control form-control-lg" />
                    <div class="" id= "feedback-confirmPassword">
                        <p class ="errorMessage">{{ form.errors.phonenumber }}&nbsp;</p>
                    </div>
                </div>
            </div>-->
        </div>


        <div class="row">
            <div class="col-md-12 mb-4 pb-2">
                <div class="form-outline">
                    <label class="form-label" for="emailAddress">Profile Picture</label>
                    <input type="file" class="form-control form-control-lg" v-on:change="form.values.profilePicture" placeholder="Photo" capture @blur="validate('profilePicture')" />
                    <div class="" id= "feedback-confirmPassword">
                        <p class ="errorMessage">{{ form.errors.profilePicture }}&nbsp;</p>
                    </div>
                </div>
            </div>
        </div>
        
        <!--<div>
            <p v-if="!!form.errors.fname" class="text-danger">
                {{ form.errors.fname }}
            </p>
            <p v-if="!!form.errors.lname" class="text-danger">
                {{ form.errors.lname }}
            </p>
            <p v-if="!!form.errors.username" class="text-danger">
                {{ form.errors.username }}
            </p>
            <p v-if="!!form.errors.emailR" class="text-danger">
                {{ form.errors.emailR }}
            </p>
            <p v-if="!!form.errors.passwordR" class="text-danger">
                {{ form.errors.passwordR }}
            </p>
            <p v-if="!!form.errors.confirmPassword" class="text-danger">
                {{ form.errors.confirmPassword }}
            </p>
            <p v-if="!!form.errors.dateOfBirth" class="text-danger">
                {{ form.errors.dateOfBirth }}
            </p>
            <p v-if="!!form.errors.profilePicture" class="text-danger">
                {{ form.errors.profilePicture }}
            </p>

        </div>-->


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
export default {


    name: "RegisterForm",
    data: () => ({
        form: {
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
        }
    }),
    methods: {
        //Register Button
        async register() {

            console.log(this.form.values.fname)
            console.log(this.form.values.lname)
            console.log(this.form.values.username)
            console.log(this.form.values.emailR)
            console.log(this.form.values.passwordR)
            console.log(this.form.values.confirmPassword)
            console.log(this.form.values.dateOfBirth)
            console.log(this.form.values.profilePicture)

            registerFormSchema
                .validate(this.form.values, { abortEarly: true })
                .then(() => {
                    
                    this.form.errors = {

                        fname: "",
                        lname: "",
                        username: "",
                        emailR: "",
                        passwordR: "",
                        confirmPassword: "",
                        dateOfBirth: "",
                        profilePicture: ""
                    }
                })
                .catch((err) => {
                    console.log(err.path)
                    
                    
                    this.form.errors[err.path] = err.message
                    window.$("#" + err.path).removeClass("is-valid");
                    window.$("#" + err.path).addClass("is-invalid");
                    window.$("#feedback-" + err.path).removeClass("valid-feedback");
                    window.$("#feedback-" + err.path).addClass("invalid-feedback");
                    /*err.inner.forEach(() => {
                        console.log("1" + err.errors.path)
                        this.form.errors[err.path] = err.message
                            
                    })*/
                    
                    
                })
        },
        validate(field) {
            registerFormSchema
                .validateAt(field, this.form.values)
                .then(() => {
                    this.form.errors[field] = "Looks Good."
                    console.log(field);
                    window.$("#" + field).removeClass("is-invalid");
                    window.$("#" + field).addClass("is-valid");
                    window.$("#feedback-" + field).removeClass("invalid-feedback");
                    window.$("#feedback-" + field).addClass("valid-feedback");
                    
                    
                })
                .catch((error) => {
                    this.form.errors[field] = error.message
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
    username: string().min(8, "Username must be at least 8 Characters!").required("Username is required!"),
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



.errorMessage{
    font-size: 11px;
    margin-bottom: 0%;
}
</style>