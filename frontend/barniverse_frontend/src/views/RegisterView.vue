<template>
    <div class="row justify-content-center align-items-center">
        <div class="col-12 col-sm-11 col-md-9 col-lg-9 col-xl-6">
            <RegisterForm :trigger="trigger" />

            <div class="pt-1">
                <input class="btn btn-primary" type="submit" v-on:click.prevent="triggerValidation" value="Register" />
            </div>
            <div class="mt-4">
                <router-link id="login" class="link" to="/login">
                    Already have an account?
                </router-link>
            </div>
        </div>
    </div>
</template>

<script>
import http from "@/http"
import RegisterForm from '@/components/forms/RegisterForm.vue';

export default {
    name: "RegisterView",
    components: { RegisterForm },
    data: () => ({
        trigger: false,
    }),
    mounted() {
        window.event.on("validationCompleted", (data) => {
            this.saveUser(data)
        });
    },
    unmounted() {
        window.event.all.delete("validationCompleted");
    },
    methods: {
        triggerValidation() {
            this.trigger = !this.trigger
        },
        async saveUser(data) {
            try {
                const requestBody = {
                    firstname: data.firstname,
                    lastname: data.lastname,
                    email: data.email,
                    username: data.username,
                    password: data.password,
                }
                const response = await http.post("/register", requestBody)
                sessionStorage.setItem("jwt-token", response.data['jwt-token']);
                window.event.emit("reloadJWT");
                this.$router.push('/')
                const modalData = {
                    title: "Welcome in the Barniverse!",
                    text: "User created successfully!"
                }
                window.event.emit("showErrorModal", modalData);
            } catch (error) {
                console.log(error)
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
        },
    }
}
</script>

<style>
</style>