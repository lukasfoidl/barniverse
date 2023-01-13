<template>

    <div class="form-outline">
        <!-- Firstname-->
        <label class="form-label" for="firstname">First Name</label>
        <input type="text" class="form-control" v-model="value" :id="'firstname' + objectId" @blur="validate(false)" />
        <div class="" :id="'feedback-firstname' + objectId">
            <p :id="'error-firstname' + objectId" class="errorMessage">{{ error }}&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "FirstNameInput",
    props: ["trigger", "firstname", "objectId"],
    data: () => ({
        value: "",
    }),
    mounted() {
        this.value = this.firstname
    },
    methods: {
        validate(shouldSendEvent) {
            var values = { firstname: this.value }; // necessary for successful validation (field/value object)
            const data = {
                field: "firstname",
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
    firstname: string().required("First name is required!"),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>