<template>

    <div class="form-outline">
        <!-- MaxPrice -->
        <label class="form-label" for="maxPrice">Maximum Price</label>
        <input type="number" :id="'maxPrice' + objectId" class="form-control" v-model="value" @blur="validate(false)" />
        <div class="" :id="'feedback-maxPrice' + objectId">
            <p :id="'error-maxPrice' + objectId" class="errorMessage">&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "MaxPriceInput",
    props: ["trigger", "maxPrice", "objectId", "minPriceInputValue"],
    data: () => ({
        value: "",
        minPrice: ""
    }),
    mounted() {
        this.value = this.maxPrice
        this.minPrice = this.minPriceInputValue

        window.event.on("minPriceChanged", (data) => {
            this.minPrice = data
        });
    },
    methods: {
        validate(shouldSendEvent) {
            var values = { maxPrice: this.value, minPrice: this.minPrice }; // necessary for successful validation (field/value object)
            const data = {
                field: "maxPrice",
                values: values,
                objectId: this.objectId,
                shouldSendEvent: shouldSendEvent,
                validationSchema: validationSchema
            }
            window.event.emit("validateInput", data)
        }
    },
    watch: { 
        trigger: function() {
            this.validate(true)
        }
    }
}

const validationSchema = object().shape({
    maxPrice: string()
        .required("Maximum price is required!")
        .matches(/^\d+[.,]?\d{0,2}$/, "Wrong format (max. of two positions after decimal point)!")
        .min(0, "Maximum price must be positive or zero!")
        .test('maxPrice-greater-minPrice', 'Maximum price must be greater than or equal Minimum price!', function () {
        return this.parent.minPrice <= this.parent.maxPrice
    }),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>