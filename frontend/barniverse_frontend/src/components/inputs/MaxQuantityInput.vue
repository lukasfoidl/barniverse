<template>

    <div class="form-outline">
        <!-- MaxQuantity -->
        <label class="form-label" for="maxQuantity">Maximum Quantity</label>
        <input type="number" :id="'maxQuantity' + objectId" class="form-control" v-model="value" @blur="validate(false)" />
        <div class="" :id="'feedback-maxQuantity' + objectId">
            <p :id="'error-maxQuantity' + objectId" class="errorMessage">&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "MaxQuantityInput",
    props: ["trigger", "maxQuantity", "objectId"],
    data: () => ({
        value: "",
        minQuantity: ""
    }),
    mounted() {
        this.value = this.maxQuantity

        window.event.on("minQuantityChanged", (data) => {
            this.minQuantity = data
        });
    },
    methods: {
        validate(shouldSendEvent) {
            var values = { maxQuantity: this.value, minQuantity: this.minQuantity }; // necessary for successful validation (field/value object)
            const data = {
                field: "maxQuantity",
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
    maxQuantity: string()
        .required("Maximum quantity is required!")
        .matches(/^\d+[.,]?\d{0,2}$/, "Wrong format (max. of two positions after decimal point)!")
        .min(0, "Maximum quantity must be positive or zero!")
        .test('maxQuantity-greater-minQuantity', 'Maximum quantity must be greater than or equal Minimum quantity!', function () {
        return this.parent.minQuantity <= this.parent.maxQuantity
    }),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>