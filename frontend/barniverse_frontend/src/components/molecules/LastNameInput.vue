<template>

    <div class="form-outline">
        <!-- Lastname-->
        <label class="form-label" for="lastname">Last Name</label>
        <input type="text" class="form-control" v-model="values.lastname" :id="'lastname' + userId" @blur="validate('lastname', false)" />
        <div class="" :id="'feedback-lastname' + userId">
            <p class="errorMessage">{{ errors.lastname }}&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "LastNameInput",
    props: ["trigger", "lastname", "userId"],
    data: () => ({
        values: {
            lastname: "",
        },
        errors: {
            lastname: "",
        },
    }),
    mounted() {
        this.values.lastname = this.lastname
    },
    methods: {
        validate(field, shouldSendEvent) {
            registerFormSchema
                .validateAt(field, this.values)
                .then(() => {
                    this.errors[field] = ""
                    window.$("#" + field + this.userId).removeClass("is-invalid");
                    window.$("#" + field + this.userId).addClass("is-valid");
                    window.$("#feedback-lastname" + field + this.userId).removeClass("invalid-feedback");
                    window.$("#feedback-lastname" + field + this.userId).addClass("valid-feedback");
                    this.sendValidationResults(field, shouldSendEvent)
                })
                .catch((error) => {
                    this.errors[field] = error.message
                    window.$("#" + field + this.userId).removeClass("is-valid");
                    window.$("#" + field + this.userId).addClass("is-invalid");
                    window.$("#feedback-lastname" + this.userId).removeClass("valid-feedback");
                    window.$("#feedback-lastname" + this.userId).addClass("invalid-feedback");
                })
        },
        sendValidationResults(field, shouldSendEvent) {
            if (shouldSendEvent) { // only send event if validation was triggered by trigger/button and not single validation
                const modalData = {
                    field: field,
                    value: this.values.lastname,
                    userId: this.userId
                }
                window.event.emit("validationSuccessful", modalData);
            }
        }
    },
    watch: { 
        trigger: function() {
            this.validate("lastname", true)
        }
    }
}

//validate here
const registerFormSchema = object().shape({
    lastname: string().required("Last name is required!"),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>