<template>
    <div class="container">
    <div class=" rounded card usercard mt-5 mb-5">
        <div class="row">
            <div class="col-md-4 border-right">
                <div class="d-flex flex-column align-items-center text-center p-3 py-5">
                    <!--https://st3.depositphotos.com/15648834/17930/v/600/depositphotos_179308454-stock-illustration-unknown-person-silhouette-glasses-profile.jpg-->
                    <img class="rounded-circle mt-5 userImg" id= "img" :src="currentProfilePicture">
                    <!--<span class="fullName" id = "firstnameShow">{{this.form.values.firstname}} &nbsp;{{this.form.values.lastname}}</span>
                    <span class="font-weight-bold" id = "usernameShow">{{this.form.values.username}}</span>
                    <span class="text-black-50" id = "emailShow">{{this.form.values.email}}</span>-->
                    <div class=" col-md-12 mt-3">
                        <input class ="mt-2 form-control form-control-sm"  id ="file" type="file" @change="onChangePicture">
                        <button class="mt-4 btn btn-primary" @click="saveProfilePicture">save Pricture</button>
                    </div>
                    <span> </span>
                </div>
            </div>
        <div class="col-md-6 border-right">
            <div class="p-3 py-5">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h4 class="text-right">MyProfile</h4>
                </div>
                <div class="row mt-2">
                    <div class="col-md-6">
                        <label class="labels">First Name </label>
                        <input type="text" class="form-control"  v-model="this.form.values.firstname"  id = "firstnameEdit" @blur="validate('firstname')"  >
                        <div class="" id= "feedback-firstname">
                            <p class ="errorMessage">{{ form.errors.firstname }}&nbsp;</p>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <label class="labels">Last Name </label>
                        <input type="text" class="form-control" v-model="this.form.values.lastname"  id = "lastnameEdit" @blur="validate('lastname')" >
                        <div class="" id= "feedback-lastname">
                            <p class ="errorMessage">{{ form.errors.lastname }}&nbsp;</p>
                        </div>
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col-md-12">
                        <label class="labels">Username</label>
                        <input type="text" class="form-control" id = "usernameEdit"  v-model="this.form.values.username" @blur="validate('username')" >
                        <div class="" id= "feedback-username">
                            <p class ="errorMessage">{{ form.errors.username }}&nbsp;</p>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <label class="labels">Email</label>
                        <input type="text" class="form-control" id = "emailEdit"  v-model="this.form.values.email" @blur="validate('email')" >
                        <div class="" id= "feedback-email">
                            <p class ="errorMessage">{{ form.errors.email }}&nbsp;</p>
                        </div>
                    </div>
                    <!--
                    <div class="col-md-12">
                        <label class="labels">New Password</label>
                        <input type="password" class="form-control" v-model="form.values.password" @blur="validate('password')">
                        <div class="" id= "feedback-password">
                            <p class ="errorMessage">{{ form.errors.password }}&nbsp;</p>
                        </div>
                    </div>
                    <div class="col-md-12">
                        <label class="labels">Confirm New Password</label>
                        <input type="password" class="form-control" v-model="form.values.confirmPassword" @blur="validate('confirmPassword')">
                        <div class="" id= "feedback-password">
                            <p class ="errorMessage">{{ form.errors.confirmPassword }}&nbsp;</p>
                        </div>
                    </div>
                    -->
                </div>
                
                
                <div class="mt-5 text-center"><button class="btn btn-primary profile-button" type="button"  @click="validateAndUpdate">save Changes</button></div>
                <div class="mt-5 text-center"><button class="btn btn-danger" type="button" @click="deactivateProfile" >delete Profile</button></div>
            </div>
        </div>
    </div>
</div>
</div>
</template>

<script>

import axios from "axios"
import {  object, string } from "yup"
import http from "@/http"


export default {
    name: "UserForm",
    data: () => ({
        currentProfilePicture: "" ,
        currentUserData:{},
        form:{
            values:{},
            errors:{}
        }
    }),
    props:["user"],
    methods: {
        //validate function when the save changes button it pressed
        async validateAndUpdate() {

            console.log(this.form.values.firstname)
            console.log(this.form.values.lastname)
            console.log(this.form.values.username)
            console.log(this.form.values.email)
            console.log(this.form.values.password)
            console.log(this.form.values.confirmPassword)
            console.log(this.form.values.profilePicture)

        editUserFormSchema
            .validate(this.form.values, { abortEarly: false })
            .then(() => {
                this.form.errors = {

                    firstname: "",
                    lastname: "",
                    username: "",
                    email: "",
                    password: "",
                    confirmPassword: "",
                    profilePicture: ""
                }
                this.updateUser();
            })
            .catch((errors) => {
                    errors.inner.forEach(element => {
                        console.log(element.path)
                        this.form.errors[element.path] = element.message
                    })
            })
        },
        //validate function for a field if the input has changed
        validate(field) {
            editUserFormSchema
                .validateAt(field, this.form.values)
                .then(() => {
                    this.form.errors[field] = ""
                    window.$("#" + field + "Edit").removeClass("is-invalid");
                    window.$("#feedback-" + field).removeClass("invalid-feedback");
                  
                })
                .catch((error) => {
                    this.form.errors[field] = error.message
                    window.$("#" + field + "Edit").addClass("is-invalid");
                    window.$("#feedback-" + field).addClass("invalid-feedback");
                })
        },
        // -
        async updateUser(){
            try {
                const response = await axios ({
                    method: "put", 
                    url:"http://localhost:8081/api/users",
                    data: {
                        id: 4, //where do we get the uid ??
                        firstname: this.form.values.firstname, 
                        lastname: this.form.values.lastname,
                        username: this.form.values.username, 
                        //password: "password", --> how to update password ? 
                        email: this.form.values.email, 
                        picture: this.form.values.picture, 
                        isAdmin: this.form.values.isAdmin, 
                        status: this.form.values.status
                    }
                })
                console.log(response)
                alert("User has been updated!")
                this.$router.go()
            } catch(error) {
                console.error(error)
            }
        }, 
        
        saveProfilePicture(){
            try{
                //localStorage.clear(); function to clear local storage
                const reader = new FileReader();
                let rawImg;
                reader.onload =  () =>{
                    rawImg = reader.result;
                    console.log(rawImg)
                    localStorage.setItem(this.form.values.id, rawImg);
                    this.form.values.newProfilePicture = rawImg;
                    document.getElementById("img").src = localStorage.getItem(this.form.values.id)
                }
                reader.readAsDataURL(this.form.values.profilePicture);
                
                
                
            }catch(e){
                console.log(e);
            }
            
            
        }, 
        onChangePicture(e){
            console.log("selected file", e.target.files[0]);
            this.form.values.profilePicture = e.target.files[0];
        },
        async deactivateProfile(){
            console.log(this.currentUserData.firstname)
            console.log(this.currentUserData.lastname)
            console.log(this.currentUserData.email)
            console.log(this.currentUserData.username)
            console.log(this.currentUserData.isAdmin)
            console.log(this.currentUserData.status)
            try {
                const response = await axios ({
                    method: "put", 
                    url:"http://localhost:8081/api/users",
                    data: {
                        id: 4, //where do we get the uid ??
                        firstname: this.currentUserData.firstname, 
                        lastname: this.currentUserData.lastname,
                        username: this.currentUserData.username, 
                        //password: "password", --> how to update password ? 
                        email: this.currentUserData.email, 
                        picture: this.currentUserData.picture, 
                        isAdmin: this.currentUserData.isAdmin, 
                        status: 2 // 0-active 1-blocked 2-deleted
                    }
                })
                console.log(response)
                alert("User has been deleted!")
                this.$router.go()
            } catch(error) {
                console.error(error)
            }
        }, 
        async requestUser() {
            try {
                const response = await http.get("users/4")
                this.form.values = response.data
                this.currentUserData["id"] = response.data.id
                this.currentUserData["firstname"] = response.data.firstname
                this.currentUserData["lastname"] = response.data.lastname
                this.currentUserData["username"] = response.data.username
                this.currentUserData["email"] = response.data.email
                this.currentUserData["picture"] = response.data.picture
                this.currentUserData["isAdmin"] = response.data.admin
                this.currentUserData["status"] = response.data.status
                this.currentUserData["password"] = response.data.password
                this.currentProfilePicture = localStorage.getItem(this.form.values.id)

                console.log(response)
                console.log(this.currentProfilePicture)
                console.log(this.form.values)
            } catch(error) {
                console.error(error)
            }

        },
        
 
    },
    beforeMount(){
        this.requestUser();
    } 
    
    
    
    
}

const editUserFormSchema = object().shape({
    firstname: string().required("First Name is required!"),
    lastname: string().required("Last Name is reuired!"),
    username: string().min(8, "Username must be at least 8 Characters!").required("Username is required!"),
    email: string().email("Email must be valid!").required("Email is required!"),
    /*password: string().min(8, "Password must be at least 8 Characters!").required("Password is required!"),
    confirmPassword: string().min(8, "Password must be at least 8 Characters!").required("Confirm Password is required!").test('passwords-match', 'Passwords must match', function (value) {
        return this.parent.password === value
    }),*/
    profilePicture: string()


})
</script>

<style>
.userImg{
    width: 150px;
    height: 150px;
    border-style: solid;
    border-color:#ebdbc7;
}
.fullName{
    font-size: 20px;
}
.usercard{
    max-width: 100%;
    background-color: #ebdbc7;
}
</style>