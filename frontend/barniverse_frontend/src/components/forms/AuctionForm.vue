<template>
    <TitleInput :trigger="trigger" :title="auctionData.title" :objectId="auctionData.id" />
    <DescriptionInput :trigger="trigger" :description="auctionData.description" :objectId="auctionData.id" />
    <div class="row">
        <div class="col-md-6">
            <MinPriceInput :trigger="trigger" :minPrice="auctionData.minPrice" :objectId="auctionData.id" />
        </div>
        <div class="col-md-6">
            <MaxPriceInput :trigger="trigger" :maxPrice="auctionData.maxPrice" :objectId="auctionData.id" />
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <MinQuantityInput :trigger="trigger" :minQuantity="auctionData.minQuantity" :objectId="auctionData.id" />
        </div>
        <div class="col-md-6">
            <MaxQuantityInput :trigger="trigger" :maxQuantity="auctionData.maxQuantity" :objectId="auctionData.id" />
        </div>
    </div>

    <InputValidationHandler :trigger="trigger" :object="auctionData" :values="values"/>
</template>

<script>
import DescriptionInput from '@/components/inputs/DescriptionInput.vue';
import TitleInput from '@/components/inputs/TitleInput.vue';
import InputValidationHandler from '@/components/inputs/InputValidationHandler.vue';
import MaxPriceInput from '@/components/inputs/MaxPriceInput.vue';
import MinPriceInput from '@/components/inputs/MinPriceInput.vue';
import MinQuantityInput from '../inputs/MinQuantityInput.vue';
import MaxQuantityInput from '../inputs/MaxQuantityInput.vue';

export default {
    name: "AuctionForm",
    components: { DescriptionInput, TitleInput, InputValidationHandler, MinPriceInput, MaxPriceInput, MinQuantityInput, MaxQuantityInput },
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