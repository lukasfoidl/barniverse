<template>

    <div class="form-outline">
        <!-- Lastname-->
        <label class="form-label" for="lastname">Last Name</label>
        <input type="text" class="form-control" v-model="value" :id="'lastname' + objectId" @blur="validate(false)" />
        <div class="" :id="'feedback-lastname' + objectId">
            <p :id="'error-lastname' + objectId" class="errorMessage">&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "LastNameInput",
    props: ["trigger", "lastname", "objectId"],
    data: () => ({
        value: "",
    }),
    mounted() {
        this.value = this.lastname
    },
    methods: {
        validate(shouldSendEvent) {
            var values = { lastname: this.value }; // necessary for successful validation (field/value object)
            const data = {
                field: "lastname",
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
    lastname: string().max(50, "Lastname must be shorter than 50 characters!").required("Last name is required!"),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>