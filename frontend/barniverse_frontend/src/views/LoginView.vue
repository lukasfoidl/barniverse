<template>
    <div class="row justify-content-center align-items-center">
        <div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-4">
            <LoginForm :trigger="trigger"/>

            <div class="pt-1 mb-4">
                <button class="btn btn-primary" type="button" v-on:click="triggerValidation">Login</button>
            </div>

            <div>
                Don't have an account? <router-link id="register" class="link" to="/register">Register here</router-link>
            </div>
        </div>
    </div>
</template>

<script>
import http from "@/http"
import LoginForm from '@/components/forms/LoginForm.vue';

export default {
    name: "LoginView",
    components: { LoginForm },
    data: () => ({
        trigger: false,
    }),
    mounted() {
        window.event.on("validationCompleted", (data) => {
            this.authenticate(data)
        });
    },
    unmounted() {
        window.event.all.delete("validationCompleted");
    },
    methods: {
        triggerValidation() {
            this.trigger = !this.trigger // change of trigger triggers validation events of children
        },
        async authenticate(data) {
            try {
                const requestBody = {
                    email: data.email,
                    password: data.password,
                }
                const response = await http.post("/login", requestBody)
                sessionStorage.setItem("jwt-token", response.data["jwt-token"]);
                this.$router.push("/");
                window.event.emit("reloadJWT");
            } catch (error) {
                console.log(error);
                const data = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                };
                window.event.emit("showErrorModal", data);
            }
        }
    }
}
</script>

<style>

</style>