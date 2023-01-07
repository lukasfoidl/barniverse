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
        window.event.on("validateInput", async (data) => {
            if (this.objectId == data.objectId) {
                this.validate(data)
            }
        })
    },
    unmounted() {
        window.event.all.delete("validateInput");
    },
    methods: {
        validate(data) {
            data.validationSchema.validateAt(data.field, data.values)
                .then(() => {
                    window.$("#error-" + data.field + data.objectId).html("&nbsp;")
                    window.$("#" + data.field + data.objectId).removeClass("is-invalid");
                    window.$("#" + data.field + data.objectId).addClass("is-valid");
                    window.$("#feedback-" + data.field + data.objectId).removeClass("invalid-feedback");
                    window.$("#feedback-" + data.field + data.objectId).addClass("valid-feedback");
                    this.sendValidationResults(data)
                })
                .catch((error) => {
                    window.$("#error-" + data.field + data.objectId).html(error.message)
                    window.$("#" + data.field + data.objectId).removeClass("is-valid");
                    window.$("#" + data.field + data.objectId).addClass("is-invalid");
                    window.$("#feedback-" + data.field + data.objectId).removeClass("valid-feedback");
                    window.$("#feedback-" + data.field + data.objectId).addClass("invalid-feedback");
                })
        },
        sendValidationResults(data) {
            if (data.shouldSendEvent) { // only send event if validation was triggered by trigger/button and not single validation
                this.checkValidationResults(data.field, data.values[data.field])
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