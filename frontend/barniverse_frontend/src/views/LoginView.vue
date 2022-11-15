<template>
    <div>
        <div class="row justify-content-center align-items-center">
            <div class="col-12 col-lg-9 col-xl-6">
                <div class="shadow-2-strong">
                    <div class="card-body p-4">
                        <LoginForm />
                    </div>
                </div>
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