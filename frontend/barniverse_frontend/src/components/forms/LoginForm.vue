<template>
    <div>
        <EmailInput :trigger="trigger" :objectId="objectId" />
        <PasswordInput :trigger="trigger" :objectId="objectId" />

        <InputValidationHandler :trigger="trigger" :objectId="objectId" :values="values" />
    </div>
</template>

<script>
import EmailInput from "@/components/inputs/EmailInput.vue"
import PasswordInput from "@/components/inputs/PasswordInput.vue";
import InputValidationHandler from "@/components/inputs/InputValidationHandler.vue";

export default {
    name: "LoginForm",
    props: ["trigger"],
    data: () => ({
        values: [
            "email",
            "password",
        ],
        objectId: undefined
    }),
    components: { EmailInput, PasswordInput, InputValidationHandler }
}

</script>

<style>
</style>