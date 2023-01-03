<template>

    <div class="form-outline">
        <!-- Firstname-->
        <label class="form-label" for="firstname">First Name</label>
        <input type="text" class="form-control" v-model="values.firstname" :id="'firstname' + userId" @blur="validate('firstname', false)" />
        <div class="" :id="'feedback-firstname' + userId">
            <p class="errorMessage">{{ errors.firstname }}&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "FirstNameInput",
    props: ["trigger", "firstname", "userId"],
    data: () => ({
        values: {
            firstname: "",
        },
        errors: {
            firstname: "",
        },
    }),
    mounted() {
        this.values.firstname = this.firstname
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
                    this.sendValidationResults(field, shouldSendEvent);
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
                    value: this.values.firstname,
                    userId: this.userId
                }
                window.event.emit("validationSuccessful", modalData);
            }
        }
    },
    watch: { 
        trigger: function() {
            this.validate("firstname", true)
        }
    }
}

//validate here
const registerFormSchema = object().shape({
    firstname: string().required("First name is required!"),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>