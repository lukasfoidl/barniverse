<template>

    <div class="form-outline">
        <!-- Lastname-->
        <label class="form-label" for="lastname">Last Name</label>
        <input type="text" class="form-control" v-model="value" :id="'lastname' + userId" @blur="validate('lastname', false)" />
        <div class="" :id="'feedback-lastname' + userId">
            <p class="errorMessage">{{ error }}&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "LastNameInput",
    props: ["trigger", "lastname", "userId"],
    data: () => ({
        value: "",
        error: "",
    }),
    mounted() {
        this.value = this.lastname
    },
    methods: {
        validate(field, shouldSendEvent) {
            var values = { lastname: this.value }; // necessary for successful validation (field/value object)
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
            this.validate("lastname", true)
        }
    }
}

const validationSchema = object().shape({
    lastname: string().required("Last name is required!"),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>