<template>
    <NameInput :trigger="trigger" :name="productData.name" :productId="productData.id"/>
    <DescriptionInput :trigger="trigger" :description="productData.description" :productId="productData.id"/>
</template>

<script>
import DescriptionInput from '../inputs/DescriptionInput.vue';
import NameInput from '../inputs/NameInput.vue';

export default {
    name: "ProductForm",
    components: { NameInput, DescriptionInput },
    props: ["trigger", "product"],
    data: () => ({
        values: [
            "name",
            "description",
        ],
        errors: {},
        validationResults: {},
        productData: {}
    }),
    beforeMount() {
        this.productData = this.product;
    },
    mounted() {
        window.event.on("validationSuccessful", async (data) => {
            if (this.productData.id == data.productId) {
                this.checkValidationResults(data);
            }
        })
    },
    unmounted() {
        window.event.all.delete("validationSuccessful");
    },
    methods: {
        async checkValidationResults(data) {
            // save validation results
            this.validationResults[data.field] = data.value;

            // only if all results received
            if (Object.keys(this.validationResults).length === this.values.length) {
                // check if all values have been successfully validated and added to validationResults
                for (var index in this.values) {
                    var foundKey = false
                    for (var key in this.validationResults) {
                        if (this.values[index] === key) {
                            foundKey = true;
                            break;
                        }
                    }
                    if (!foundKey) {
                        return;
                    }
                }
                const modalData = {
                    id: this.productData.id,
                    name: this.validationResults.name,
                    description: this.validationResults.description,
                    images: []
                }
                window.event.emit("validationCompleted", modalData);                    
            }
        },
    },
    watch: { 
        trigger: function() {
            this.validationResults = {}
        }
    }
}
</script>

<style>
</style>