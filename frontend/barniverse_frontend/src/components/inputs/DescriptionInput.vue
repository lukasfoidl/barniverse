<template>

    <div class="form-outline">
        <!-- Description-->
        <label class="form-label" for="description">Description</label>
        <textarea type="textarea" rows="5" class="form-control" v-model="value" :id="'description' + objectId" @blur="validate(false)" />
        <div class="" :id="'feedback-description' + objectId">
            <p :id="'error-description' + objectId" class="errorMessage">&nbsp;</p>
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
    }),
    mounted() {
        this.value = this.description
    },
    methods: {
        validate(shouldSendEvent) {
            var values = { description: this.value }; // necessary for successful validation (field/value object)
            const data = {
                field: "description",
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
    description: string().max(500, "Description must be shorter than 500 characters!"),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>