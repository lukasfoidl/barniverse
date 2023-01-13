<template>
    <router-link v-if="isAdmin" id="createProduct" class="createStyle" to="/products/create">
        <i class="bi bi-plus-square-fill" alt="Create new product"></i>
    </router-link>
</template>

<script>
export default {
    name: "Taskbar",
    computed: {
        isAdmin() {
            return this.$store.getters.isAdmin
        }
    }
}
</script>

<style>
.createStyle, .createStyle:hover {
    font-size: 25px;
    color: green;
}
</style>