<template>

    <Navbar />

    <router-view />

    <Footer />

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
</style>