<template>

    <div class="form-outline">
        <!-- EndDate -->
        <label class="form-label" for="endDate">End Date</label>
        <input type="datetime-local" :id="'endDate' + objectId" class="form-control" v-model="value" @change="endDateChanged" @blur="validate(false)" />
        <div class="" :id="'feedback-endDate' + objectId">
            <p :id="'error-endDate' + objectId" class="errorMessage">&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, date } from "yup"

export default {
    name: "EndDateInput",
    props: ["trigger", "endDate", "objectId", "startDateInputValue"],
    data: () => ({
        value: "",
        startDate: ""
    }),
    mounted() {
        this.value = this.endDate
        this.startDate = this.startDateInputValue

        window.event.on("startDateChanged", (data) => {
            this.startDate = data
        });
    },
    unmounted() {
        window.event.all.delete("startDateChanged");
    },
    methods: {
        validate(shouldSendEvent) {
            var values = { endDate: this.value, startDate: this.startDate}; // necessary for successful validation (field/value object)
            const data = {
                field: "endDate",
                values: values,
                objectId: this.objectId,
                shouldSendEvent: shouldSendEvent,
                validationSchema: validationSchema
            }
            window.event.emit("validateInput", data)
        },
        endDateChanged() {
            window.event.emit("endDateChanged", this.value)
        }
    },
    watch: { 
        trigger: function() {
            this.validate(true)
        }
    }
}

const validationSchema = object().shape({
    endDate: date()
        .test('endDate-isAfter-startDate', 'End date needs to be after start date!', function () {
        return new Date(this.parent.endDate) > new Date(this.parent.startDate)
    }),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>