<template>

    <div class="form-outline">
        <!-- Email-->
        <label class="form-label" for="emailAddress">Email</label>
        <input type="email" :id="'email' + userId" class="form-control" v-model="value" aria-describedby="emailHelp"
            @blur="validate('email', false)" />
        <div class="" :id="'feedback-email' + userId">
            <p class="errorMessage">{{ error }}&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "EmailInput",
    props: ["trigger", "email", "userId"],
    data: () => ({
        value: "",
        error: "",
    }),
    mounted() {
        this.value = this.email
    },
    methods: {
        validate(field, shouldSendEvent) {
            var values = { email: this.value }; // necessary for successful validation (field/value object)
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
        }
    },
    watch: { 
        trigger: function() {
            this.validate("email", true)
        }
    }
}

const validationSchema = object().shape({
    email: string().email("Email must be valid!").required("Email is required!"),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>