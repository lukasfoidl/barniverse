<template>
    <div class="row">
        <div class="col-md-6 centerPictureDiv">
            <div class="d-flex flex-column align-items-center text-center">
                <img class="rounded-circle userImg" id="img" :src="values.picture">
                <div class="col-md-12 mt-3">
                    <input class="mt-2 form-control form-control-sm" id="file" type="file" @change="onChangePicture">
                    <button class="mt-4 btn btn-primary" @click="saveProfilePicture">Upload Image</button>
                </div>
                <span> </span>
            </div>
        </div>
        <div class="col-md-6">
            <div class="row">
                <div class="col-md-6">
                    <label class="labels">First Name </label>
                    <input type="text" class="form-control" v-model="this.values.firstname" id="firstnameEdit"
                        @blur="validate('firstname')">
                    <div class="" id="feedback-firstname">
                        <p class="errorMessage">{{ errors.firstname }}&nbsp;</p>
                    </div>
                </div>
                <div class="col-md-6">
                    <label class="labels">Last Name </label>
                    <input type="text" class="form-control" v-model="this.values.lastname" id="lastnameEdit"
                        @blur="validate('lastname')">
                    <div class="" id="feedback-lastname">
                        <p class="errorMessage">{{ errors.lastname }}&nbsp;</p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <label class="labels">Username</label>
                    <input type="text" class="form-control" id="usernameEdit" v-model="this.values.username"
                        @blur="validate('username')">
                    <div class="" id="feedback-username">
                        <p class="errorMessage">{{ errors.username }}&nbsp;</p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <label class="labels">Email</label>
                    <input type="text" class="form-control" id="emailEdit" v-model="this.values.email"
                        @blur="validate('email')">
                    <div class="" id="feedback-email">
                        <p class="errorMessage">{{ errors.email }}&nbsp;</p>
                    </div>
                </div>
                <!--
                <div class="col-md-12">
                    <label class="labels">New Password</label>
                    <input type="password" class="form-control" v-model="values.password" @blur="validate('password')">
                    <div class="" id= "feedback-password">
                        <p class ="errorMessage">{{ errors.password }}&nbsp;</p>
                    </div>
                </div>
                <div class="col-md-12">
                    <label class="labels">Confirm New Password</label>
                    <input type="password" class="form-control" v-model="values.confirmPassword" @blur="validate('confirmPassword')">
                    <div class="" id= "feedback-password">
                        <p class ="errorMessage">{{ errors.confirmPassword }}&nbsp;</p>
                    </div>
                </div>
            -->
            </div>
            <div class="text-center">
                <button class="btn btn-primary buttonSpacer" @click="validateAndUpdate">
                    Save Changes
                </button>
                <button class="btn btn-danger" @click="deactivateProfile">Delete
                    Profile
                </button>
            </div>
        </div>
    </div>
</template>

<script>
import http from "@/http"
import { object, string } from "yup"
// import http from "@/http"


export default {
    name: "UserForm",
    props: ["user"],
    data: () => ({
        values: {},
        errors: {}
    }),
    mounted() {
        this.values.firstname = this.user.firstname
        this.values.lastname = this.user.lastname
        this.values.username = this.user.username
        this.values.email = this.user.email
        this.values.picture = this.user.picture
    },
    methods: {
        //validate function when the save changes button it pressed
        validateAndUpdate() {
            editUserFormSchema
                .validate(this.values, { abortEarly: false })
                .then(async () => {
                    this.updateUser();
                })
                .catch((errors) => {
                    errors.inner.forEach(element => {
                        console.log(element.path)
                        this.errors[element.path] = element.message
                    })
                })
        },
        //validate function for a field if the input has changed
        validate(field) {
            editUserFormSchema
                .validateAt(field, this.values)
                .then(() => {
                    this.errors[field] = ""
                    window.$("#" + field + "Edit").removeClass("is-invalid");
                    window.$("#feedback-" + field).removeClass("invalid-feedback");

                })
                .catch((error) => {
                    this.errors[field] = error.message
                    window.$("#" + field + "Edit").addClass("is-invalid");
                    window.$("#feedback-" + field).addClass("invalid-feedback");
                })
        },
        async updateUser() {
            try {
                const data = {
                    id: this.user.id,
                    firstname: this.values.firstname,
                    lastname: this.values.lastname,
                    username: this.values.username,
                    email: this.values.email,
                    picture: this.values.picture,
                    state: this.user.state
                }
                const response = await http.put("users", data, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                window.event.emit("reloadUsername", response.data.username)
                const modalData = {
                    title: "Info (" + response.status + ")",
                    text: "User updated successfully!"
                }
                window.event.emit("showModal", modalData);
            } catch (error) {
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showModal", modalData);
            }
        },
        onChangePicture(e) {
            this.values.picture = e.target.files[0];
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
                window.event.emit("showModal", modalData);
            } catch (error) {
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showModal", modalData);
            }
        },
    }
}

const editUserFormSchema = object().shape({
    firstname: string().required("First Name is required!"),
    lastname: string().required("Last Name is reuired!"),
    username: string().min(5, "Username must be between 5 and 20 Characters long!").max(20, "Username must be between 5 and 20 Characters long!").required("Username is required!"),
    email: string().email("Email must be valid!").required("Email is required!"),
    profilePicture: string()


})
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
    margin-right: 10px;
}

.centerPictureDiv {
    display: flex;
    justify-content: center;
    flex-direction: column;
}
</style>