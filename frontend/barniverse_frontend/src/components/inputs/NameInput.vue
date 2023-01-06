<template>

    <div class="form-outline">
        <!-- Name -->
        <label class="form-label" for="name">Name</label>
        <input type="name" :id="'name' + productId" class="form-control" v-model="values.name" aria-describedby="name"
            @blur="validate('name', false)" />
        <div class="" :id="'feedback-name' + productId">
            <p class="errorMessage">{{ errors.name }}&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "NameInput",
    props: ["trigger", "name", "productId"],
    data: () => ({
        values: {
            name: "",
        },
        errors: {
            name: "",
        },
    }),
    mounted() {
        this.values.name = this.name
    },
    methods: {
        validate(field, shouldSendEvent) {
            registerFormSchema
                .validateAt(field, this.values)
                .then(() => {
                    this.errors[field] = ""
                    window.$("#" + field + this.productId).removeClass("is-invalid");
                    window.$("#" + field + this.productId).addClass("is-valid");
                    window.$("#feedback-" + field + this.productId).removeClass("invalid-feedback");
                    window.$("#feedback-" + field + this.productId).addClass("valid-feedback");
                    this.sendValidationResults(field, shouldSendEvent)
                })
                .catch((error) => {
                    this.errors[field] = error.message
                    window.$("#" + field + this.productId).removeClass("is-valid");
                    window.$("#" + field + this.productId).addClass("is-invalid");
                    window.$("#feedback-" + field + this.productId).removeClass("valid-feedback");
                    window.$("#feedback-" + field + this.productId).addClass("invalid-feedback");
                })
        },
        sendValidationResults(field, shouldSendEvent) {
            if (shouldSendEvent) { // only send event if validation was triggered by trigger/button and not single validation
                const modalData = {
                    field: field,
                    value: this.values.name,
                    productId: this.productId
                }
                window.event.emit("validationSuccessful", modalData);
            }
        }
    },
    watch: { 
        trigger: function() {
            this.validate("name", true)
        }
    }
}

//validate here
const registerFormSchema = object().shape({
    name: string().required("Name is required!"),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>