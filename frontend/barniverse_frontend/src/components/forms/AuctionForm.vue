<template>
    <TitleInput :trigger="trigger" :title="auctionData.title" :objectId="auctionData.id"/>
    <DescriptionInput :trigger="trigger" :description="auctionData.description" :objectId="auctionData.id"/>

    <InputValidationHandler :trigger="trigger" :object="auctionData" :values="values"/>
</template>

<script>
import DescriptionInput from '@/components/inputs/DescriptionInput.vue';
import TitleInput from '@/components/inputs/TitleInput.vue';
import InputValidationHandler from '../inputs/InputValidationHandler.vue';

export default {
    name: "AuctionForm",
    components: { DescriptionInput, TitleInput, InputValidationHandler },
    props: ["trigger", "auction"],
    data: () => ({
        values: [
            "title",
            "description",
        ],
        auctionData: {}
    }),
    beforeMount() {
        this.auctionData = this.auction;
    },
}
</script>

<style>
</style>