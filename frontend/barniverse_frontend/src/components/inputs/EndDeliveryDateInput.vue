<template>

    <div class="form-outline">
        <!-- StartDeliverDate -->
        <label class="form-label" for="endDeliveryDate">End Delivery Date</label>
        <input type="datetime-local" :id="'endDeliveryDate' + objectId" class="form-control" v-model="value" @blur="validate(false)" />
        <div class="" :id="'feedback-endDeliveryDate' + objectId">
            <p :id="'error-endDeliveryDate' + objectId" class="errorMessage">&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, date } from "yup"

export default {
    name: "EndDeliveryDateInput",
    props: ["trigger", "endDeliveryDate", "objectId"],
    data: () => ({
        value: "",
        startDeliveryDate: ""
    }),
    mounted() {
        this.value = this.endDeliveryDate

        window.event.on("startDeliveryDateChanged", (data) => {
            this.startDeliveryDate = data
        });
    },
    unmounted() {
        window.event.all.delete("startDeliveryDateChanged");
    },
    methods: {
        validate(shouldSendEvent) {
            var values = { endDeliveryDate: this.value, startDeliveryDate: this.startDeliveryDate}; // necessary for successful validation (field/value object)
            const data = {
                field: "endDeliveryDate",
                values: values,
                objectId: this.objectId,
                shouldSendEvent: shouldSendEvent,
                validationSchema: validationSchema
            }
            window.event.emit("validateInput", data)
        },
    },
    watch: { 
        trigger: function() {
            this.validate(true)
        }
    }
}

const validationSchema = object().shape({
    endDeliveryDate: date()
        .test('endDeliveryDate-isAfter-startDeliveryDate', 'End delivery date needs to be after start delivery date!', function () {
        return new Date(this.parent.endDeliveryDate) > new Date(this.parent.startDeliveryDate)
    }),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>