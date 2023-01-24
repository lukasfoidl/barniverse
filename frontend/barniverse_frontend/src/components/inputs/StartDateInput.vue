<template>

    <div class="form-outline">
        <!-- StartDate -->
        <label class="form-label" for="startDate">Start Date</label>
        <input type="datetime-local" :id="'startDate' + objectId" class="form-control" v-model="value" @change="startDateChanged" @blur="validate(false)" />
        <div class="" :id="'feedback-startDate' + objectId">
            <p :id="'error-startDate' + objectId" class="errorMessage">&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, date } from "yup"

export default {
    name: "StartDateInput",
    props: ["trigger", "startDate", "objectId"],
    data: () => ({
        value: "",
    }),
    mounted() {
        this.value = this.startDate
        this.startDateChanged();
    },
    methods: {
        validate(shouldSendEvent) {
            var values = { startDate: this.value }; // necessary for successful validation (field/value object)
            const data = {
                field: "startDate",
                values: values,
                objectId: this.objectId,
                shouldSendEvent: shouldSendEvent,
                validationSchema: validationSchema
            }
            window.event.emit("validateInput", data)
        },
        startDateChanged() {
            window.event.emit("startDateChanged", this.value)
        }
    },
    watch: { 
        trigger: function() {
            this.validate(true)
        }
    }
}

const validationSchema = object().shape({
    startDate: date()
        .test('startDate-isIn-future', 'Start date needs to be in the future!', function () {
        return new Date(this.parent.startDate) > new Date()
    }),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>