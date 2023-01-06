<template>
    <div class="row">
        <div class="col-md-6">
            <FirstNameInput :trigger="this.trigger" :firstname="this.user.firstname" :userId="this.user.id" />
        </div>
        <div class="col-md-6">
            <LastNameInput :trigger="this.trigger" :lastname="this.user.lastname" :userId="this.user.id" />
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <EmailInput :trigger="this.trigger" :email="this.user.email" :userId="this.user.id" />
        </div>
        <div class="col-md-6">
            <UsernameInput :trigger="this.trigger" :username="this.user.username" :userId="this.user.id" />
        </div>
    </div>

    <InputValidationHandler :trigger="trigger" :objectId="this.user.id" :values="values" />
</template>

<script>
import FirstNameInput from "@/components/inputs/FirstNameInput.vue"
import LastNameInput from "@/components/inputs/LastNameInput.vue"
import EmailInput from "@/components/inputs/EmailInput.vue"
import UsernameInput from "@/components/inputs/UsernameInput.vue"
import InputValidationHandler from "../inputs/InputValidationHandler.vue"

export default {
    name: "UserForm",
    props: ["trigger", "user"],
    data: () => ({
        values: [
                "firstname",
                "lastname",
                "username",
                "email",
            ],
    }),
    components: { FirstNameInput, LastNameInput, EmailInput, UsernameInput, InputValidationHandler },
}
</script>

<style>
</style>