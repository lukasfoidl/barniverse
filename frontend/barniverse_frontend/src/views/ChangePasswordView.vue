<template>
    <div class="row justify-content-center align-items-center">
        <div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-4">
            <ChangePasswordForm :trigger="trigger" />

            <div class="text-center">
                <button class="btn btn-primary buttonSpacer" @click="triggerValidation">Save</button>
                <button class="btn btn-secondary buttonSpacer" @click="cancel">Cancel</button>
            </div>
        </div>
    </div>
</template>

<script>
import http from "@/http"
import ChangePasswordForm from '@/components/forms/ChangePasswordForm.vue';

export default {
    name: "ChangePasswordView",
    components: { ChangePasswordForm },
    data: () => ({
        trigger: false,
    }),
    mounted() {
        window.event.on("validationCompleted", (data) => {
            this.changePassword(data)
        });
    },
    unmounted() {
        window.event.all.delete("validationCompleted");
    },
    methods: {
        triggerValidation() {
            this.trigger = !this.trigger // change of trigger triggers validation events of children
        },
        async changePassword(data) {
            try {
                const requestBody = {
                    id: this.$store.state.uuid,
                    password: data.password,
                }
                const response = await http.put("users/changePassword", requestBody, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                const modalData = {
                    title: "Info (" + response.status + ")",
                    text: "Password updated successfully!"
                }
                window.event.emit("showErrorModal", modalData);
                this.$router.push("/profile")
            } catch (error) {
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
        },
        cancel() {
            this.$router.push("/profile")
        }
    }
}
</script>

<style>
.buttonSpacer {
   margin: 5px;
}
</style>