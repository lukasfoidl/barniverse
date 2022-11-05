<template>

    <Navbar />

    <router-view />

</template>

<script>
import Navbar from "./components/templates/Navbar.vue"

export default {
    name: 'App',
    components: {
        Navbar
    }
}
</script>

<style>
</style>