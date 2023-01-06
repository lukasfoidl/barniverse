<template>

    <div class="form-outline">
        <!-- Description-->
        <label class="form-label" for="description">Description</label>
        <textarea type="textarea" rows="5" class="form-control" v-model="values.description" :id="'description' + productId" @blur="validate('description', false)" />
        <div class="" :id="'feedback-description' + productId">
            <p class="errorMessage">{{ errors.description }}&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    description: "DescriptionInput",
    props: ["trigger", "description", "productId"],
    data: () => ({
        values: {
            description: "",
        },
        errors: {
            description: "",
        },
    }),
    mounted() {
        this.values.description = this.description
    },
    methods: {
        validate(field, shouldSendEvent) {
            registerFormSchema
                .validateAt(field, this.values)
                .then(() => {
                    this.errors[field] = ""
                    window.$("#" + field + this.productId).removeClass("is-invalid");
                    window.$("#" + field + this.productId).addClass("is-valid");
                    window.$("#feedback-description" + field + this.productId).removeClass("invalid-feedback");
                    window.$("#feedback-description" + field + this.productId).addClass("valid-feedback");
                    this.sendValidationResults(field, shouldSendEvent)
                })
                .catch((error) => {
                    this.errors[field] = error.message
                    window.$("#" + field + this.productId).removeClass("is-valid");
                    window.$("#" + field + this.productId).addClass("is-invalid");
                    window.$("#feedback-description" + this.productId).removeClass("valid-feedback");
                    window.$("#feedback-description" + this.productId).addClass("invalid-feedback");
                })
        },
        sendValidationResults(field, shouldSendEvent) {
            if (shouldSendEvent) { // only send event if validation was triggered by trigger/button and not single validation
                const modalData = {
                    field: field,
                    value: this.values.description,
                    productId: this.productId
                }
                window.event.emit("validationSuccessful", modalData);
            }
        }
    },
    watch: { 
        trigger: function() {
            this.validate("description", true)
        }
    }
}

//validate here
const registerFormSchema = object().shape({
    description: string().max(500, "Description must be shorter than 500 characters!"),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>