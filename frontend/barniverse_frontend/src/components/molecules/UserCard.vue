<template>
    <div class =" container mt-5">
        <div class="card userCard " >
             <div class="row no-gutters">
                <div class="col-md-3 imageUser">
                    <img :src="this.profilePicture" class=" rounded-circle imgFormat" alt="...">
                </div>
                <div class="col-md-4">
                    <div class="card-body ">
                        <h6>#{{user.id}}</h6>
                        <h5 class=" mb-2 card-title">{{user.firstname}} {{user.lastname}}</h5>


                        

                        <div class="mt-3">
                            <span> Username: </span>
                            
                            <span class="" v-if="!this.allowEdit">{{user.username}}</span>
                            <input class="form-control" v-if="this.allowEdit" type="text" v-model="this.editData.username">
                        </div>
                        
                        <div class="mt-3">
                            <span> E-Mail: </span>
                            <span class="" v-if="!this.allowEdit" >{{user.email}}</span>
                            <input  class="form-control" v-if="this.allowEdit" type="text" v-model="this.editData.email">
                        </div>
                        <div class="mt-3">
                            <span v-if="!this.allowEdit"> Admin-Rights: </span>
                            <span v-if="this.allowEdit">Firstname:</span>
                            <span class="" v-if="!this.allowEdit">{{user.admin}}</span>
                            <input class="form-control" v-if="this.allowEdit" type="text" v-model="this.editData.firstname">
                        </div>
                        <div class="mt-3">
                            <span v-if="!this.allowEdit"> Status: </span>
                            <span v-if="this.allowEdit">Lastname:</span>
                            <span v-if="!this.allowEdit">{{user.status}}</span>
                            <input class="form-control"  v-if="this.allowEdit" type="text" v-model="this.editData.lastname">
                            
                        </div>
                        <div class="mt-3">
                            <button v-if="this.allowEdit" type="submit" class="btn btn-primary" @click="updateThisUser">save changes</button>
                        </div>
                        
                        
                    </div>
                </div>
                <div class="col-md-2 text-center">
                    <div class="mt-3 "><button class=" btn btn-primary buttonAdmin" @click="allowEditUser">edit User</button></div>
                    <div class="mt-3 "><button v-if="blocked" class="btn btn-primary buttonAdmin blockUser" @click="blockThisUser">block User</button></div>
                    <div class="mt-3 "><button v-if="!blocked" class="btn btn-success buttonAdmin " @click="activateThisUser">activate User</button></div>
                    <div class="mt-3 "><button class="btn btn-danger buttonAdmin" @click="deleteThisUser">delete User</button></div>
                    <div class="mt-3 "><button class="btn btn-primary buttonAdmin adminRights" @click="changeAdminRights">change Admin-Rights</button></div>

                </div>
            </div>
        </div>
        

    </div>
</template>
<script>

import axios from "axios"


export default{
    name: "UserCard", 
    props:["user"],
    data: () => ({
        allowEdit: false,
        blocked: false,
        //standard pic 
        profilePicture:"https://media.istockphoto.com/id/1288129985/de/vektor/fehlendes-bild-einer-person-platzhalter.jpg?s=612x612&w=0&k=20&c=frRQgPIXZnd3qHP5H8jjJp8oGfceWRGmJbKs7nILHic=",
        editData:{
            id: "",
            firstname: "",
            lastname:"",
            username:"",
            email:"",
            admin:"",
            status:"",
            picture:""
        }
    
    }), 
    methods:{
        allowEditUser(){
            this.allowEdit =!this.allowEdit
            this.editData["id"] = this.user.id
            this.editData["firstname"] = this.user.firstname
            this.editData["lastname"] = this.user.lastname
            this.editData["username"] = this.user.username
            this.editData["email"] = this.user.email
            this.editData["admin"] = this.user.admin
            this.editData["status"] = this.user.status
        }, 
        async updateThisUser(){
            //function to update User
            try {
                const response = await axios ({
                    method: "put", 
                    url:"http://localhost:8081/api/users",
                    data: {
                        id: this.editData.id, 
                        firstname: this.editData.firstname, 
                        lastname: this.editData.lastname,
                        username: this.editData.username, 
                        //password: "password", --> how to update password ? 
                        email: this.editData.email, 
                        picture: "", 
                        is_admin: this.editData.admin, 
                        status: this.editData.status
                    }
                })
                console.log(response)
                alert("User has been updated!")
                this.$router.go()
            } catch(error) {
                console.error(error)
            }
        }, 
        async deleteThisUser(){
            try {
                const response = await axios ({
                    method: "put", 
                    url:"http://localhost:8081/api/users",
                    data: {
                        id: this.user.id, 
                        firstname: this.user.firstname, 
                        lastname: this.user.lastname,
                        username: this.user.username, 
                        //password: "password", --> how to update password ? 
                        email: this.user.email, 
                        picture: "", 
                        admin: this.user.admin, 
                        status: 2 //this 0 active 1 blocked 2 deleted
                    }
                })
                console.log(response)
                alert("User has been updated!")
                this.$router.go()
            } catch(error) {
                console.error(error)
            }
        }, 
        async blockThisUser(){
            try {
                const response = await axios ({
                    method: "put", 
                    url:"http://localhost:8081/api/users",
                    data: {
                        id: this.user.id, 
                        firstname: this.user.firstname, 
                        lastname: this.user.lastname,
                        username: this.user.username, 
                        //password: "password", --> how to update password ? 
                        email: this.user.email, 
                        picture: "", 
                        admin: this.user.admin, 
                        status: 1 //this 0 active 1 blocked 2 deleted
                    }
                })
                console.log(response)
                alert("User has been updated!")
                this.$router.go()
            } catch(error) {
                console.error(error)
            }
        }, 
        async changeAdminRights(){
            //admin is allowed to assign admin rights ? 
            try {
                const response = await axios ({
                    method: "put", 
                    url:"http://localhost:8081/api/users",
                    data: {
                        id: this.user.id, 
                        firstname: this.user.firstname, 
                        lastname: this.user.lastname,
                        username: this.user.username, 
                        
                        email: this.user.email, 
                        picture: "", 
                        admin: !this.user.admin, //switch true false
                        status: this.user.status 
                    }
                })
                console.log(response)
                alert("User has been updated!")
                this.$router.go()
            } catch(error) {
                console.error(error)
            }
        }, 
        async activateThisUser(){
            try {
                const response = await axios ({
                    method: "put", 
                    url:"http://localhost:8081/api/users",
                    data: {
                        id: this.user.id, 
                        firstname: this.user.firstname, 
                        lastname: this.user.lastname,
                        username: this.user.username, 
                        email: this.user.email, 
                        picture: "", 
                        admin: this.user.admin, 
                        status: 0 //this 0 active 1 blocked 2 deleted
                    }
                })
                console.log(response)
                alert("User has been updated!")
                this.$router.go()
            } catch(error) {
                console.error(error)
            }
        }

    },
    beforeMount(){
        console.log(this.user.status)
        if(this.user.status =="active"){
            this.blocked= true; 
        }

        if(localStorage.getItem(this.user.id)){
            this.profilePicture = localStorage.getItem(this.user.id)
        }
        
    }
}
</script>
<style>
.imageUser{
    padding-top: 40px;
}
.imgFormat{
    width: 150px;
    height: 150px;
    border-style: solid;
    border-color: #ebdbc7; 
    display:block;
    margin:auto;
   
}
.userCard{
    max-width: 100%;
    background-color: #ebdbc7;
}
.buttonAdmin{
    width: 200px;
    margin-bottom: 10px;
}
.blockUser{
    background-color: grey;
    border-color:grey;
}
.adminRights{
    background-color: coral;
    border-color:coral;
}
</style>