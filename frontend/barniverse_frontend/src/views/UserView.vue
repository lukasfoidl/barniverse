<template>
    <div>

        <UserCard v-for="user in allUser" :key="user.id" :user="user"> </UserCard>
    </div>
</template>

<script>
import UserCard from "@/components/molecules/UserCard.vue"
import http from "../http"

export default {
    name: "UserView",
    components: { UserCard },
    data: () => ({
        allUser: []
    }),
    methods: {
        async requestUsers() {
            try {
                const response = await http.get("users", {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                this.allUser = response.data
            } catch(error) {
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
        },
    },
    beforeMount() {
        this.requestUsers();
    }
}

</script>
<style>

</style>