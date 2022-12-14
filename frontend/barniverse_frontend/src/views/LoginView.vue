<template>
    <div id="backgroundRegister">
        <div class="row justify-content-center align-items-center">
            <div class="col-12 col-lg-9 col-xl-6">
                <LoginForm />
            </div>
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