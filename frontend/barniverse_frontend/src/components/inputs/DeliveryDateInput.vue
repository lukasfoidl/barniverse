<template>

    <div class="form-outline">
        <!-- DeliverDate -->
        <label class="form-label" for="deliveryDate">Delivery Date</label>
        <input type="datetime-local" :id="'deliveryDate' + objectId" class="form-control" v-model="value" @blur="validate(false)" />
        <div class="" :id="'feedback-deliveryDate' + objectId">
            <p :id="'error-deliveryDate' + objectId" class="errorMessage">&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, date } from "yup"

export default {
    name: "DeliveryDateInput",
    props: ["trigger", "objectId"],
    data: () => ({
        value: "",
    }),
    mounted() {
        this.value = this.deliveryDate
    },
    methods: {
        validate(shouldSendEvent) {
            var values = { deliveryDate: this.value}; // necessary for successful validation (field/value object)
            const data = {
                field: "deliveryDate",
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
    deliveryDate: date()
        .test('deliveryDate-isIn-future', 'Delivery date needs to be in the future!', function () {
            return new Date(this.parent.deliveryDate) > new Date()
    }),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>