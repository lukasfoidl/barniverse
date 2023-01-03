<template>
    <div class="row justify-content-center align-items-center">
        <div class="col-12 col-md-12 col-lg-10 col-xl-8">
            <div class="accordion" id="userList">
                <UserAccordionElement v-for="user in allUser" :key="user.id" :user="user" />
            </div>
        </div>
    </div>
</template>

<script>
import UserAccordionElement from "@/components/molecules/UserAccordionElement.vue"
import http from "../http"

export default {
    name: "UserView",
    components: { UserAccordionElement },
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
                this.allUser = response.data.sort(
                    (a, b) => (a.lastname > b.lastname) ? 1 : (a.lastname < b.lastname) ? -1 : 0);
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
    },
}

</script>
<style>

</style>