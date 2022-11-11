<template>

    <Navbar />

    <div class="mainContent">
        <router-view />
    </div>

    <div class="footer">
        <Footer />
    </div>

</template>

<script>
import Navbar from "./components/templates/Navbar.vue"
import Footer from "./components/templates/Footer.vue"

export default {
    name: 'App',
    components: {
        Navbar,
        Footer
    }
}
</script>

<style>
.mainContent {
    margin-left: 20px;
    margin-right: 20px;
    flex: 1 1 100%;
}

.footer {
    flex: 0 0 auto;
}
</style>