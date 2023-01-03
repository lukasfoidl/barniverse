<template>
    <div class="row justify-content-center align-items-center">
        <div class="col-12 col-sm-11 col-md-9 col-lg-9 col-xl-6">
            <RegisterForm />
        </div>
    </div>
</template>

<script>
import RegisterForm from '@/components/forms/RegisterForm.vue';

export default {
    name: "RegisterView",
    components: { RegisterForm }
}
</script>

<style>
</style>