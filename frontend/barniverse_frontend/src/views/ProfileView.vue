<template>
    <div class="row justify-content-center align-items-center">
        <div class="col-12 col-sm-11 col-md-9 col-lg-9 col-xl-6">
            <ProfileWorker v-if="user.length != 0" :key="user.id" :user="user"/>
        </div>
    </div>
</template>

<script>
import ProfileWorker from '@/components/forms/ProfileWorker.vue';
import http from "@/http"

export default {
    name: "ProfileView",
    data: () => ({
        user: []
    }),
    components:{ ProfileWorker }, 
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
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
        },
    },
    beforeMount() {
        this.requestUser();
    }
}
</script>
