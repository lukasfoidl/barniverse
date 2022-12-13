<template>

    <Navbar />

    <div class="mainContent">
        <router-view />
    </div>

    <div class="footer">
        <Footer />
    </div>

    <Modal />
</template>

<script>
import Navbar from "./components/templates/Navbar.vue"
import Footer from "./components/templates/Footer.vue"
import Modal from "./components/molecules/Modal.vue";

import jwtDecoder from 'vue-jwt-decode'

export default {
    name: 'App',
    components: {
        Navbar,
        Footer,
        Modal
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
            window.role = jwt == null ? "" : jwt.role
            window.username = jwt == null ? "" : jwt.username
            window.uuid = jwt == null ? "" : jwt.uuid
        }
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