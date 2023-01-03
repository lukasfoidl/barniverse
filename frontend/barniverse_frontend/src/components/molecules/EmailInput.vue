<template>

    <div class="form-outline">
        <!-- Email-->
        <label class="form-label" for="emailAddress">Email</label>
        <input type="email" :id="'email' + userId" class="form-control" v-model="values.email" aria-describedby="emailHelp"
            @blur="validate('email', false)" />
        <div class="" :id="'feedback-email' + userId">
            <p class="errorMessage">{{ errors.email }}&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "EmailInput",
    props: ["trigger", "email", "userId"],
    data: () => ({
        values: {
            email: "",
        },
        errors: {
            email: "",
        },
    }),
    mounted() {
        this.values.email = this.email
    },
    methods: {
        validate(field, shouldSendEvent) {
            registerFormSchema
                .validateAt(field, this.values)
                .then(() => {
                    this.errors[field] = ""
                    window.$("#" + field + this.userId).removeClass("is-invalid");
                    window.$("#" + field + this.userId).addClass("is-valid");
                    window.$("#feedback-" + field + this.userId).removeClass("invalid-feedback");
                    window.$("#feedback-" + field + this.userId).addClass("valid-feedback");
                    this.sendValidationResults(field, shouldSendEvent)
                })
                .catch((error) => {
                    this.errors[field] = error.message
                    window.$("#" + field + this.userId).removeClass("is-valid");
                    window.$("#" + field + this.userId).addClass("is-invalid");
                    window.$("#feedback-" + field + this.userId).removeClass("valid-feedback");
                    window.$("#feedback-" + field + this.userId).addClass("invalid-feedback");
                })
        },
        sendValidationResults(field, shouldSendEvent) {
            if (shouldSendEvent) { // only send event if validation was triggered by trigger/button and not single validation
                const modalData = {
                    field: field,
                    value: this.values.email,
                    userId: this.userId
                }
                window.event.emit("validationSuccessful", modalData);
            }
        }
    },
    watch: { 
        trigger: function() {
            this.validate("email", true)
        }
    }
}

//validate here
const registerFormSchema = object().shape({
    email: string().email("Email must be valid!").required("Email is required!"),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>