<template>

    <Navbar />

    <div class="mainContent">
        <router-view />
    </div>

    <div class="footer">
        <Footer />
    </div>

    <ErrorModal />
    <PermissionModal />
</template>

<script>
import Navbar from "@/components/templates/Navbar.vue"
import Footer from "@/components/templates/Footer.vue"
import ErrorModal from "@/components/modals/ErrorModal.vue";
import PermissionModal from "@/components/modals/PermissionModal.vue";

import jwtDecoder from 'vue-jwt-decode'

export default {
    name: 'App',
    components: {
        Navbar,
        Footer,
        ErrorModal,
        PermissionModal
    },
    beforeMount() {
        this.reloadJWT();

        window.event.on('reloadJWT', () => {
            this.reloadJWT();
        });
    },
    methods: {
        reloadJWT() {
            const jwt = jwtDecoder.decode(sessionStorage.getItem("jwt-token") ?? "")
            this.$store.commit("setRole", { role: jwt == null ? this.$store.state.roles.ROLE_OBSERVER : jwt.role })
            this.$store.commit("setUsername", { username: jwt == null ? "" : jwt.username })
            this.$store.commit("setUUID", { uuid: jwt == null ? "" : jwt.uuid })
        },
    }
}
</script>

<style>
.mainContent {
    margin: 20px;
    flex: 1 1 100%;
}

.footer {
    flex: 0 0 auto;
}
</style>