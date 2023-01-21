<template>
    <div class="row justify-content-center align-items-center">
        <div class="col-12 col-sm-11 col-md-9 col-lg-9 col-xl-6">
            <div>
                <div>
                    <h5>{{ offer.auction.title }}</h5>
                    <div>{{ offer.auction.description }}</div>
                </div>
                <p></p>
                <div>
                    <h6>Hard Facts</h6>
                    <span>Time Range: </span>
                    <span>{{ new Date(offer.auction.startDate).toLocaleDateString('de-AT') }}</span><span>&nbsp;</span>
                    <span>{{ new Date(offer.auction.startDate).toLocaleTimeString('de-AT') }}</span>
                    <span> - </span>
                    <span>{{ new Date(offer.auction.endDate).toLocaleDateString('de-AT') }}</span><span>&nbsp;</span>
                    <span>{{ new Date(offer.auction.endDate).toLocaleTimeString('de-AT') }}</span>
                </div>
                <div>
                    <span>Price Range: </span>
                    <span>{{ offer.auction.minPrice }}</span>
                    <span> - </span>
                    <span>{{ offer.auction.maxPrice }}</span>
                    <span> €</span>
                </div>
                <div>
                    <span>Quantity Range: </span>
                    <span>{{ offer.auction.minQuantity }}</span>
                    <span> - </span>
                    <span>{{ offer.auction.maxQuantity }}</span>
                </div>
                <div>
                    <span>Delivery Range: </span>
                    <span>{{ new Date(offer.auction.startDeliveryDate).toLocaleDateString('de-AT') }}</span><span>&nbsp;</span>
                    <span>{{ new Date(offer.auction.startDeliveryDate).toLocaleTimeString('de-AT') }}</span>
                    <span> - </span>
                    <span>{{ new Date(offer.auction.endDeliveryDate).toLocaleDateString('de-AT') }}</span><span>&nbsp;</span>
                    <span>{{ new Date(offer.auction.endDeliveryDate).toLocaleTimeString('de-AT') }}</span>
                </div>
                <p></p>
                <div>
                    <h6>Contact Information</h6>
                    <span>{{ offer.auction.user.firstname }}</span><span>&nbsp;</span>
                    <span>{{ offer.auction.user.lastname }}</span><span>&nbsp;(</span>
                    <span>{{ offer.auction.user.username }}</span><span>): </span>
                    <span>{{ offer.auction.user.email }}</span>
                </div>

                <p class="space"></p>

                <div>
                    <h5>{{ offer.auction.product.title }}</h5>
                    <div>{{ offer.auction.product.description }}</div>
                </div>

                <p class="space"></p>

                <div>
                    <h5>Offer</h5>
                </div>
                <div>
                    <span>Price: </span>
                    <span>{{ offer.price }}</span>
                    <span> €</span>
                </div>
                <div>
                    <span>Quantity: </span>
                    <span>{{ offer.quantity }}</span>
                </div>
                <div>
                    <span>Delivery Date: </span>
                    <span>{{ new Date(offer.deliveryDate).toLocaleDateString('de-AT') }}</span><span>&nbsp;</span>
                    <span>{{ new Date(offer.deliveryDate).toLocaleTimeString('de-AT') }}</span>
                </div>
            </div>

            <p class="space"></p>

            <div>
                <h5>Thank you for using Barniverse.</h5>
            </div>

            

            <div class="text-center">
                <button class="btn btn-secondary buttonSpacer" @click="cancel">Cancel</button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "OfferDetailsView",
    components: { },
    data: () => ({
        offer: {},
    }),
    beforeMount() {
        this.offer = this.$store.state.offer;
    },
    methods: {
        cancel() {
            this.$router.push("/myOffers");
        }
    }
}

</script>

<style>
.space {
    margin: 40px;
}
</style>