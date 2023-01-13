<template>

    <div class="form-outline">
        <!-- StartDeliverDate -->
        <label class="form-label" for="startDeliveryDate">Start Delivery Date</label>
        <input type="datetime-local" :id="'startDeliveryDate' + objectId" class="form-control" v-model="value" @change="startDeliveryDateChanged" @blur="validate(false)" />
        <div class="" :id="'feedback-startDeliveryDate' + objectId">
            <p :id="'error-startDeliveryDate' + objectId" class="errorMessage">&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, date } from "yup"

export default {
    name: "StartDeliveryDateInput",
    props: ["trigger", "startDeliveryDate", "objectId"],
    data: () => ({
        value: "",
        endDate: "",
    }),
    mounted() {
        this.value = this.startDeliveryDate

        window.event.on("endDateChanged", (data) => {
            this.endDate = data
        });
    },
    methods: {
        validate(shouldSendEvent) {
            var values = { startDeliveryDate: this.value, endDate: this.endDate}; // necessary for successful validation (field/value object)
            const data = {
                field: "startDeliveryDate",
                values: values,
                objectId: this.objectId,
                shouldSendEvent: shouldSendEvent,
                validationSchema: validationSchema
            }
            window.event.emit("validateInput", data)
        },
        startDeliveryDateChanged() {
            window.event.emit("startDeliveryDateChanged", this.value)
        }
    },
    watch: { 
        trigger: function() {
            this.validate(true)
        }
    }
}

const validationSchema = object().shape({
    startDeliveryDate: date()
        .test('startDeliveryDate-isIn-future', 'Start delivery date needs to be after end date!', function () {
        return new Date(this.parent.startDeliveryDate) > new Date(this.parent.endDate)
    }),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>