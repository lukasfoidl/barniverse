<template>

    <div class="form-outline">
        <!-- Price -->
        <label class="form-label" for="Price">Price</label>
        <input type="number" :id="'price' + objectId" class="form-control" v-model="value" @blur="validate(false)" />
        <div class="" :id="'feedback-price' + objectId">
            <p :id="'error-price' + objectId" class="errorMessage">&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "PriceInput",
    props: ["trigger", "objectId"],
    data: () => ({
        value: "",
    }),
    mounted() {
        this.value = this.price
    },
    methods: {
        validate(shouldSendEvent) {
            var values = { price: this.value }; // necessary for successful validation (field/value object)
            const data = {
                field: "price",
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
    price: string()
        .required("Price is required!")
        .matches(/^\d+[.,]?\d{0,2}$/, "Wrong format (max. of two positions after decimal point)!")
        .min(0, "Price must be positive or zero!")
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>