<template>
    <div>
        <PasswordInput :trigger="trigger" />
        <ConfirmationPasswordInput :trigger="trigger" />

        <InputValidationHandler :trigger="trigger" :objectId="objectId" :values="values" />
    </div>
</template>

<script>
import PasswordInput from "@/components/inputs/PasswordInput.vue";
import InputValidationHandler from "@/components/inputs/InputValidationHandler.vue";
import ConfirmationPasswordInput from "../inputs/ConfirmationPasswordInput.vue";

export default {
    name: "ChangePasswordForm",
    props: ["trigger"],
    data: () => ({
        values: [
            "password",
            "confirmationPassword",
        ],
        objectId: undefined
    }),
    components: { PasswordInput, ConfirmationPasswordInput, InputValidationHandler, }
}

</script>

<style>
</style>