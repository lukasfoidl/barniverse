<template>

    <div class="form-outline">
        <!-- MinQuantity -->
        <label class="form-label" for="minQuantity">Minimum Quantity</label>
        <input type="number" :id="'minQuantity' + objectId" class="form-control" v-model="value" @change="minQuantityChanged" @blur="validate(false)" />
        <div class="" :id="'feedback-minQuantity' + objectId">
            <p :id="'error-minQuantity' + objectId" class="errorMessage">&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "MinQuantityInput",
    props: ["trigger", "minQuantity", "objectId"],
    data: () => ({
        value: "",
    }),
    mounted() {
        this.value = this.minQuantity
    },
    methods: {
        validate(shouldSendEvent) {
            var values = { minQuantity: this.value }; // necessary for successful validation (field/value object)
            const data = {
                field: "minQuantity",
                values: values,
                objectId: this.objectId,
                shouldSendEvent: shouldSendEvent,
                validationSchema: validationSchema
            }
            window.event.emit("validateInput", data)
        },
        minQuantityChanged() {
            window.event.emit("minQuantityChanged", this.value)
        }
    },
    watch: { 
        trigger: function() {
            this.validate(true)
        }
    }
}

const validationSchema = object().shape({
    minQuantity: string()
        .required("Minimum quantity is required!")
        .matches(/^\d+[.,]?\d{0,2}$/, "Wrong format (max. of two positions after decimal point)!")
        .min(0, "Minimum quantity must be positive or zero!")
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>