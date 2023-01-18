<template>
    <div>
        <label class="form-label" for="product">Product</label>
        <div id="product">
            <span>{{ product.title }}</span>
            <span>&nbsp;</span>
            <TitleDescriptionPopover :object="product" />
        </div>
    </div>
    <TitleInput :trigger="trigger" :title="auctionData.title" :objectId="auctionData.id" />
    <DescriptionInput :trigger="trigger" :description="auctionData.description" :objectId="auctionData.id" />
    <div class="row">
        <div class="col-md-6">
            <MinPriceInput :trigger="trigger" :minPrice="auctionData.minPrice" :objectId="auctionData.id" />
        </div>
        <div class="col-md-6">
            <MaxPriceInput :trigger="trigger" :maxPrice="auctionData.maxPrice" :objectId="auctionData.id" :minPriceInputValue="auctionData.minPrice"/>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <MinQuantityInput :trigger="trigger" :minQuantity="auctionData.minQuantity" :objectId="auctionData.id" />
        </div>
        <div class="col-md-6">
            <MaxQuantityInput :trigger="trigger" :maxQuantity="auctionData.maxQuantity" :objectId="auctionData.id" :minQuantityInputValue="auctionData.minQuantity" />
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <StartDateInput :trigger="trigger" :startDate="auctionData.startDate" :objectId="auctionData.id" />
        </div>
        <div class="col-md-6">
            <EndDateInput :trigger="trigger" :endDate="auctionData.endDate" :objectId="auctionData.id" :startDateInputValue="auctionData.startDate" />
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <StartDeliveryDateInput :trigger="trigger" :startDeliveryDate="auctionData.startDeliveryDate"
                :objectId="auctionData.id" :endDateInputValue="auction.endDate" />
        </div>
        <div class="col-md-6">
            <EndDeliveryDateInput :trigger="trigger" :endDeliveryDate="auctionData.endDeliveryDate"
                :objectId="auctionData.id" :startDeliveryDateInputValue="auction.startDeliveryDate" />
        </div>
    </div>

    <InputValidationHandler :trigger="trigger" :objectId="auctionData.id" :values="values" />
</template>

<script>
import DescriptionInput from '@/components/inputs/DescriptionInput.vue';
import TitleInput from '@/components/inputs/TitleInput.vue';
import InputValidationHandler from '@/components/inputs/InputValidationHandler.vue';
import MaxPriceInput from '@/components/inputs/MaxPriceInput.vue';
import MinPriceInput from '@/components/inputs/MinPriceInput.vue';
import MinQuantityInput from '../inputs/MinQuantityInput.vue';
import MaxQuantityInput from '../inputs/MaxQuantityInput.vue';
import StartDeliveryDateInput from '../inputs/StartDeliveryDateInput.vue';
import EndDeliveryDateInput from '../inputs/EndDeliveryDateInput.vue';
import StartDateInput from '../inputs/StartDateInput.vue';
import EndDateInput from '../inputs/EndDateInput.vue';
import TitleDescriptionPopover from '../molecules/TitleDescriptionPopover.vue';

export default {
    name: "AuctionForm",
    components: { DescriptionInput, TitleInput, InputValidationHandler, MinPriceInput, MaxPriceInput, MinQuantityInput, MaxQuantityInput, StartDeliveryDateInput, EndDeliveryDateInput, StartDateInput, EndDateInput, TitleDescriptionPopover },
    props: ["trigger", "auction", "product"],
    data: () => ({
        values: [
            "title",
            "description",
            "minPrice",
            "maxPrice",
            "minQuantity",
            "maxQuantity",
            "startDate",
            "endDate",
            "startDeliveryDate",
            "endDeliveryDate"
        ],
        auctionData: {}
    }),
    beforeMount() {
        this.auctionData = this.auction;
    },
}
</script>

<style>
#product {
    margin-bottom: 11px;
}

.infoIcon {
    color: black;
}
</style>