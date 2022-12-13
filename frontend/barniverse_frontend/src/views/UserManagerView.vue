<template>
    <div>
        
        <UserCard v-for="user in allUser" :key ="user.id" :user = "user"> </UserCard>
    </div>
</template>
<script>

import axios from "axios";
import UserCard from "@/components/molecules/UserCard.vue"

export default{
    name: "UserManagerView", 
    components:{ UserCard },
    data: ()=> ({
        allUser: []
    }),
    methods: {
        async getAllUser(){
            try {
                const response = await axios ({
                    method: "get", 
                    url:"http://localhost:8081/api/users",
                    data: {}
                })
                console.log(response)
                this.allUser = response.data
            } catch(error) {
                console.error(error)
            }
        }
    }, 
    beforeMount(){
        this.getAllUser();
    }
}

</script>
<style>

</style>