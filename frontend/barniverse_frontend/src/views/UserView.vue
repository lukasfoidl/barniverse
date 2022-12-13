<template>
    <div>
        <UserForm />
    </div>
</template>

<script>
import UserForm from '@/components/forms/UserForm.vue';
//import http from "../http"

export default {
    name: "UserView",
    data: () => ({
        user: [], 
        
    }),
    components:{ UserForm }, 
    methods: {
        /*async requestUser() {
            try {
                const response = await http.get("users/4")
                this.user = response.data
                console.log(response)
            } catch(error) {
                console.error(error)
            }

        },*/
        
        
    },
    beforeMount() {
        //this.requestUser();
    }
}
</script>

<style>
</style>