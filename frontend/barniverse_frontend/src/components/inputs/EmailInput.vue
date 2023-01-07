<template>

    <div class="form-outline">
        <!-- Email-->
        <label class="form-label" for="emailAddress">Email</label>
        <input type="email" :id="'email' + objectId" class="form-control" v-model="value" aria-describedby="emailHelp" @blur="validate(false)" />
        <div class="" :id="'feedback-email' + objectId">
            <p :id="'error-email' + objectId" class="errorMessage">&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "EmailInput",
    props: ["trigger", "email", "objectId"],
    data: () => ({
        value: "",
    }),
    mounted() {
        this.value = this.email
    },
    methods: {
        validate(shouldSendEvent) {
            var values = { email: this.value }; // necessary for successful validation (field/value object)
            const data = {
                field: "email",
                values: values,
                objectId: this.objectId,
                shouldSendEvent: shouldSendEvent,
                validationSchema: validationSchema
            }
            window.event.emit("validateInput", data)
        }
    },
    watch: { 
        trigger: function() {
            this.validate(true)
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