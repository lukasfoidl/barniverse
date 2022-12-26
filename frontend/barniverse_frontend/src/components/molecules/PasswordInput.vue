<template>

    <div class="form-outline">
        <!-- Password-->
        <label class="form-label" for="password">Password</label>
        <input type="password" id="password" class="form-control" v-model="values.password" @change="passwordInputChanged"
            @blur="validate('password', false)" />
        <div class="" id="feedback-password">
            <p class="errorMessage">{{ errors.password }}&nbsp;</p>
        </div>
    </div>

</template>

<script>
import { object, string } from "yup"

export default {
    name: "PasswordInput",
    props: ["trigger"],
    data: () => ({
        values: {
            password: "",
        },
        errors: {
            password: "",
        },
    }),
    methods: {
        validate(field, shouldSendEvent) {
            registerFormSchema
                .validateAt(field, this.values)
                .then(() => {
                    this.errors[field] = ""
                    window.$("#" + field).removeClass("is-invalid");
                    window.$("#" + field).addClass("is-valid");
                    window.$("#feedback-" + field).removeClass("invalid-feedback");
                    window.$("#feedback-" + field).addClass("valid-feedback");
                    this.sendValidationResults(field, shouldSendEvent)
                })
                .catch((error) => {
                    this.errors[field] = error.message
                    window.$("#" + field).removeClass("is-valid");
                    window.$("#" + field).addClass("is-invalid");
                    window.$("#feedback-" + field).removeClass("valid-feedback");
                    window.$("#feedback-" + field).addClass("invalid-feedback");
                })
        },
        sendValidationResults(field, shouldSendEvent) {
            if (shouldSendEvent) { // only send event if validation was triggered by trigger/button and not single validation
                const modalData = {
                    field: field,
                    value: this.values.password,
                }
                window.event.emit("validationSuccessful", modalData);
            }
        },
        passwordInputChanged() {
            window.event.emit("passwordInputChanged", this.values.password);
        }
    },
    watch: { 
        trigger: function() {
            this.validate("password", true)
        }
    }
}

//validate here
const registerFormSchema = object().shape({
    password: string().min(8, "Password must be at least 8 Characters!").required("Password is required!"),
})
</script>

<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>