<template>
    <TitleInput :trigger="trigger" :title="productData.title" :objectId="productData.id"/>
    <DescriptionInput :trigger="trigger" :description="productData.description" :objectId="productData.id"/>

    <InputValidationHandler :trigger="trigger" :objectId="productData.id" :values="values" />
</template>

<script>
import DescriptionInput from '@/components/inputs/DescriptionInput.vue';
import TitleInput from '@/components/inputs/TitleInput.vue';
import InputValidationHandler from '@/components/inputs/InputValidationHandler.vue';

export default {
    name: "ProductForm",
    components: { DescriptionInput, TitleInput, InputValidationHandler },
    props: ["trigger", "product"],
    data: () => ({
        values: [
            "title",
            "description",
        ],
        productData: {}
    }),
    beforeMount() {
        this.productData = this.product;
    },
}
</script>

<style>
</style>