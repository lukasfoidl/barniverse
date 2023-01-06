<template>
    <router-link v-if="role == roles.ROLE_ADMIN" id="createProduct" class="createStyle" to="/products/create">
        <i class="bi bi-plus-square-fill" alt="Create new product"></i>
    </router-link>
</template>

<script>
export default {
    name: "Taskbar",
    data: () => ({
        role: "",
        roles: "",
    }),
    mounted() {
        this.role = window.role;
        this.roles = window.roles;
    }
}
</script>

<style>
.createStyle, .createStyle:hover {
    font-size: 25px;
    color: green;
}
</style>