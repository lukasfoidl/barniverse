<template>
    <div class="invisible"></div>
</template>

<script>
export default {
    name: "InputValidationHandler",
    props: ["trigger", "objectId", "values"],
    data: () => ({
        errors: {},
        validationResults: {},
    }),
    mounted() {
        window.event.on("updateValidationSuccess", async (data) => {
            this.updateValidationSuccess(data.shouldSendEvent, data.field, data.value, data.objectId)
        })
        window.event.on("updateValidationError", async (data) => {
            this.updateValidationError(data.field, data.objectId)
        })
    },
    unmounted() {
        window.event.all.delete("updateValidationSuccess");
        window.event.all.delete("updateValidationError");
    },
    methods: {
        updateValidationSuccess(shouldSendEvent, field, value, objectId) {
            window.$("#" + field + objectId).removeClass("is-invalid");
            window.$("#" + field + objectId).addClass("is-valid");
            window.$("#feedback-" + field + objectId).removeClass("invalid-feedback");
            window.$("#feedback-" + field + objectId).addClass("valid-feedback");
            this.sendValidationResults(shouldSendEvent, field, value, objectId)
        },
        updateValidationError(field, objectId) {
            window.$("#" + field + objectId).removeClass("is-valid");
            window.$("#" + field + objectId).addClass("is-invalid");
            window.$("#feedback-" + field + objectId).removeClass("valid-feedback");
            window.$("#feedback-" + field + objectId).addClass("invalid-feedback");
        },
        sendValidationResults(shouldSendEvent, field, value, objectId) {
            if (shouldSendEvent) { // only send event if validation was triggered by trigger/button and not single validation
                if (this.objectId == objectId) {
                    this.checkValidationResults(field, value)
                }
            }
        },
        async checkValidationResults(field, value) {
            // save validation results
            this.validationResults[field] = value;

            // only if all results received
            if (Object.keys(this.validationResults).length === this.values.length) {
                // check if all values have been successfully validated and added to validationResults
                for (var index in this.values) {
                    var foundKey = false
                    for (var key in this.validationResults) {
                        if (this.values[index] === key) {
                            foundKey = true;
                            break;
                        }
                    }
                    if (!foundKey) {
                        return;
                    }
                }
                this.validationResults.id = this.objectId
                window.event.emit("validationCompleted", this.validationResults);                    
            }
        },
    },
    watch: { 
        trigger: function() {
            this.validationResults = {}
        }
    }
}
</script>

<style>
</style>