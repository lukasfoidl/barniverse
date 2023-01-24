<template>

    <div class="form-outline">
        <!-- Quantity -->
        <label class="form-label" for="quantity">Quantity</label>
        <input type="number" :id="'quantity' + objectId" class="form-control" v-model="value" @blur="validate(false)" />
        <div class="" :id="'feedback-quantity' + objectId">
            <p :id="'error-quantity' + objectId" class="errorMessage">&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "QuantityInput",
    props: ["trigger", "objectId"],
    data: () => ({
        value: "",
    }),
    mounted() {
        this.value = this.quantity
    },
    methods: {
        validate(shouldSendEvent) {
            var values = { quantity: this.value }; // necessary for successful validation (field/value object)
            const data = {
                field: "quantity",
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
    quantity: string()
        .required("Quantity is required!")
        .matches(/^\d+[.,]?\d{0,2}$/, "Wrong format (max. of two positions after decimal point)!")
        .min(0, "Quantity must be positive or zero!")
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>