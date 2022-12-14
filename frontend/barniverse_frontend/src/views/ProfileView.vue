<template>
    <div id="backgroundRegister">
        <div class="row justify-content-center align-items-center">
            <div class="col-12 col-lg-9 col-xl-6">
                <UserForm :key="user.id" :user="user"/>
            </div>
        </div>
    </div>
</template>

<script>
import UserForm from '@/components/forms/UserForm.vue';
import http from "../http"

export default {
    name: "ProfileView",
    data: () => ({
        user: []
    }),
    components:{ UserForm }, 
    methods: {
        async requestUser() {
            try {
                const response = await http.get("users/" + window.uuid, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                this.user = response.data
            } catch(error) {
                const data = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showModal", data);
            }
        },
    },
    beforeMount() {
        this.requestUser();
    }
}
</script>
