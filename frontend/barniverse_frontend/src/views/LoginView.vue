<template>
    <div class="">
        <section >
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-6 text-black">

                        <div class="px-5 ms-xl-4">
                            <i class="fas fa-crow fa-2x me-3 pt-5 mt-xl-4" style="color: #709085;"></i>
                            <span class="h1 fw-bold mb-0">Welcome!</span>
                        </div>

                         <div class="d-flex align-items-center h-custom-2 px-5 ms-xl-4 mt-5 pt-xl-0 mt-xl-n5 ">
                            <LoginForm/>
                         </div>

                    </div>
                    <div class="col-sm-6 px-0 d-none d-sm-block">
                        <img src="https://i.pinimg.com/564x/52/fb/77/52fb77d63bb37409039820cf84be7b4b.jpg" class ="rounded mb-3" alt="Login image"  id="loginImage">
                    </div>
                </div>
            </div>
        </section>
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