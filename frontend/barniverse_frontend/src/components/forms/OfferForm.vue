<template>
    <div>
        <AuctionCard :auction="auctionData" :isSingleView="true" />
    </div>
    <div class="row">
        <div class="col-md-4">
            <PriceInput :trigger="trigger" :objectId="objectId" />
        </div>
        <div class="col-md-4">
            <QuantityInput :trigger="trigger" :objectId="objectId" />
        </div>
        <div class="col-md-4">
            <DeliveryDateInput :trigger="trigger" :objectId="objectId" />
        </div>
    </div>

    <InputValidationHandler :trigger="trigger" :objectId="objectId" :values="values" />
</template>

<script>
import InputValidationHandler from '@/components/inputs/InputValidationHandler.vue';
import PriceInput from '@/components/inputs/PriceInput.vue';
import QuantityInput from '@/components/inputs/QuantityInput.vue';
import DeliveryDateInput from '@/components/inputs/DeliveryDateInput.vue';
import AuctionCard from '../cards/AuctionCard.vue';

export default {
    name: "OfferForm",
    components: { InputValidationHandler, PriceInput, QuantityInput, DeliveryDateInput, AuctionCard },
    props: ["trigger", "auction"],
    data: () => ({
        values: [
            "price",
            "quantity",
            "deliveryDate"
        ],
        auctionData: {},
        objectId: undefined
    }),
    beforeMount() {
        this.auctionData = this.auction;
    },
}
</script>

<style>
#product, #article {
    margin-bottom: 11px;
}

.infoIcon {
    color: black;
}
</style>