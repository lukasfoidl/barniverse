<template>
    <div class="row justify-content-center align-items-center">
        <div class="col-12 col-md-9 col-lg-6 col-xl-6">
            <LoginForm />
        </div>
    </div>
</template>

<script>
import LoginForm from '@/components/forms/LoginForm.vue';

export default {
    name: "LoginView",
    components: { LoginForm }
}
</script>

<style>

</style>