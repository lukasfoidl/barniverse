<template>

    <div class="form-outline">
        <!-- Confirm Password-->
        <label class="form-label" for="confirmationPassword">Confirm Password</label>
        <input type="password" id="confirmationPassword" class="form-control" v-model="values.confirmationPassword"
            @blur="validate('confirmationPassword', false)" />
        <div class="" id="feedback-confirmationPassword">
            <p class="errorMessage">{{ errors.confirmationPassword }}&nbsp;</p>
        </div>
    </div>

</template>

<script>
import { object, string } from "yup"

export default {
    name: "ConfirmationPasswordInput",
    props: ["trigger"],
    data: () => ({
        values: {
            password: "",
            confirmationPassword: ""
        },
        errors: {
            confirmationPassword: ""
        },
    }),
    mounted() {
        window.event.on("passwordInputChanged", (data) => {
            this.passwordInputChanged(data)
        });
    },
    unmounted() {
        window.event.all.delete("passwordInputChanged");
    },
    methods: {
        validate(field, shouldSendEvent) {
            registerFormSchema
                .validateAt(field, this.values)
                .then(() => {
                this.errors[field] = "";
                window.$("#" + field).removeClass("is-invalid");
                window.$("#" + field).addClass("is-valid");
                window.$("#feedback-" + field).removeClass("invalid-feedback");
                window.$("#feedback-" + field).addClass("valid-feedback");
                this.sendValidationResults(field, shouldSendEvent);
            })
                .catch((error) => {
                this.errors[field] = error.message;
                window.$("#" + field).removeClass("is-valid");
                window.$("#" + field).addClass("is-invalid");
                window.$("#feedback-" + field).removeClass("valid-feedback");
                window.$("#feedback-" + field).addClass("invalid-feedback");
            });
        },
        sendValidationResults(field, shouldSendEvent) {
            if (shouldSendEvent) { // only send event if validation was triggered by trigger/button and not single validation
                const modalData = {
                    field: field,
                    value: this.values.confirmationPassword
                };
                window.event.emit("validationSuccessful", modalData);
            }
        },
        passwordInputChanged(value) {
            this.values.password = value
        },
    },
    watch: {
        trigger: function () {
            this.validate("confirmationPassword", true);
        }
    },
}

//validate here
const registerFormSchema = object().shape({
    confirmationPassword: string().min(8, "Password must be at least 8 Characters!").required("Confirm Password is required!").test('passwords-match', 'Passwords must match', function (value) {
        return this.parent.password === value
    }),
})
</script>

<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>