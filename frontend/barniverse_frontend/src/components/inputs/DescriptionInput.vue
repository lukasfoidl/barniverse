<template>

    <div class="form-outline">
        <!-- Description-->
        <label class="form-label" for="description">Description</label>
        <textarea type="textarea" rows="5" class="form-control" v-model="value" :id="'description' + objectId" @blur="validate('description', false)" />
        <div class="" :id="'feedback-description' + objectId">
            <p class="errorMessage">{{ error }}&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    description: "DescriptionInput",
    props: ["trigger", "description", "objectId"],
    data: () => ({
        value: "",
        error: "",
    }),
    mounted() {
        this.value = this.description
    },
    methods: {
        validate(field, shouldSendEvent) {
            var values = { description: this.value }; // necessary for successful validation (field/value object)
            validationSchema
                .validateAt(field, values)
                .then(() => {
                    this.error = ""
                    const data = {
                        field: field,
                        value: this.value,
                        objectId: this.objectId,
                        shouldSendEvent: shouldSendEvent
                    }
                    window.event.emit("updateValidationSuccess", data)
                })
                .catch((error) => {
                    this.error = error.message
                    const data = {
                        field: field,
                        objectId: this.objectId
                    }
                    window.event.emit("updateValidationError", data)
                })
        }
    },
    watch: { 
        trigger: function() {
            this.validate("description", true)
        }
    }
}

const validationSchema = object().shape({
    description: string().max(500, "Description must be shorter than 500 characters!"),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>