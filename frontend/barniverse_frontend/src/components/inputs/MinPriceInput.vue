<template>

    <div class="form-outline">
        <!-- MinPrice -->
        <label class="form-label" for="minPrice">Minimum Price</label>
        <input type="number" :id="'minPrice' + objectId" class="form-control" v-model="value" @change="minPriceChanged" @blur="validate(false)" />
        <div class="" :id="'feedback-minPrice' + objectId">
            <p :id="'error-minPrice' + objectId" class="errorMessage">&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "MinPriceInput",
    props: ["trigger", "minPrice", "objectId"],
    data: () => ({
        value: "",
    }),
    mounted() {
        this.value = this.minPrice
    },
    methods: {
        validate(shouldSendEvent) {
            var values = { minPrice: this.value }; // necessary for successful validation (field/value object)
            const data = {
                field: "minPrice",
                values: values,
                objectId: this.objectId,
                shouldSendEvent: shouldSendEvent,
                validationSchema: validationSchema
            }
            window.event.emit("validateInput", data)
        },
        minPriceChanged() {
            window.event.emit("minPriceChanged", this.value)
        }
    },
    watch: { 
        trigger: function() {
            this.validate(true)
        }
    }
}

const validationSchema = object().shape({
    minPrice: string()
        .required("Minimum price is required!")
        .matches(/^\d+[.,]?\d{0,2}$/, "Wrong format (max. of two positions after decimal point)!")
        .min(0, "Minimum price must be positive or zero!")
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>