<template>

    <div class="form-outline">
        <!-- Lastname-->
        <label class="form-label" for="lastname">Last Name</label>
        <input type="text" class="form-control" v-model="values.lastname" id="lastname" @blur="validate('lastname', false)" />
        <div class="" id="feedback-lastname">
            <p class="errorMessage">{{ errors.lastname }}&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "LastNameInput",
    props: ["trigger", "lastname"],
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
                    value: this.values.lastname,
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
    lastname: string().required("Last name is reuired!"),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>