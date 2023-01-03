<template>

    <div class="form-outline">
        <!-- Confirm Password-->
        <label class="form-label" for="confirmationPassword">Confirm Password</label>
        <input type="password" :id="'confirmationPassword' + userId" class="form-control" v-model="values.confirmationPassword"
            @blur="validate('confirmationPassword', false)" />
        <div class="" :id="'feedback-confirmationPassword' + userId">
            <p class="errorMessage">{{ errors.confirmationPassword }}&nbsp;</p>
        </div>
    </div>

</template>

<script>
import { object, string } from "yup"

export default {
    name: "ConfirmationPasswordInput",
    props: ["trigger", "userId"],
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
                window.$("#" + field + this.userId).removeClass("is-invalid");
                window.$("#" + field + this.userId).addClass("is-valid");
                window.$("#feedback-" + field + this.userId).removeClass("invalid-feedback");
                window.$("#feedback-" + field + this.userId).addClass("valid-feedback");
                this.sendValidationResults(field, shouldSendEvent);
            })
                .catch((error) => {
                this.errors[field] = error.message;
                window.$("#" + field + this.userId).removeClass("is-valid");
                window.$("#" + field + this.userId).addClass("is-invalid");
                window.$("#feedback-" + field + this.userId).removeClass("valid-feedback");
                window.$("#feedback-" + field + this.userId).addClass("invalid-feedback");
            });
        },
        sendValidationResults(field, shouldSendEvent) {
            if (shouldSendEvent) { // only send event if validation was triggered by trigger/button and not single validation
                const modalData = {
                    field: field,
                    value: this.values.confirmationPassword,
                    userId: this.userId
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