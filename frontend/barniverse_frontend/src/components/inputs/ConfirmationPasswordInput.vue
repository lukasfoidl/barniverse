<template>

    <div class="form-outline">
        <!-- Confirm Password-->
        <label class="form-label" for="confirmationPassword">Confirm Password</label>
        <input type="password" :id="'confirmationPassword' + userId" class="form-control" v-model="value"
            @blur="validate('confirmationPassword', false)" />
        <div class="" :id="'feedback-confirmationPassword' + userId">
            <p class="errorMessage">{{ error }}&nbsp;</p>
        </div>
    </div>

</template>

<script>
import { object, string } from "yup"

export default {
    name: "ConfirmationPasswordInput",
    props: ["trigger", "userId"],
    data: () => ({
        value: "",
        error: "",
        password : ""
    }),
    mounted() {
        window.event.on("passwordInputChanged", (data) => {
            this.password = data
        });
    },
    unmounted() {
        window.event.all.delete("passwordInputChanged");
    },
    methods: {
        validate(field, shouldSendEvent) {
            var values = { confirmationPassword: this.value, password: this.password }; // necessary for successful validation (field/value object)
            validationSchema
                .validateAt(field, values)
                .then(() => {
                    this.error = ""
                    const data = {
                        field: field,
                        value: this.value,
                        objectId: this.userId,
                        shouldSendEvent: shouldSendEvent
                    }
                    window.event.emit("updateValidationSuccess", data)
                })
                .catch((error) => {
                    this.error = error.message
                    const data = {
                        field: field,
                        objectId: this.userId
                    }
                    window.event.emit("updateValidationError", data)
                })
        },
    },
    watch: { 
        trigger: function() {
            this.validate("confirmationPassword", true)
        }
    }
}

const validationSchema = object().shape({
    confirmationPassword: string().min(8, "Password must be at least 8 Characters!").required("Confirm Password is required!").test('passwords-match', 'Passwords must match', function () {
        return this.parent.password === this.parent.confirmationPassword
    }),
})
</script>

<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>