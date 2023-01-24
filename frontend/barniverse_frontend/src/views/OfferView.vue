<template>
    <div class="row justify-content-center align-items-center">
        <div class="col-xs-12 col-sm-11 col-md-10 col-lg-8 col-xl-6">

            <AuctionCard :auction="auction" :isSingleView="true" />

            <div class="table-responsive">
                <table class="table table-hover table-striped">
                    <caption>
                        <span>{{ offers.length }}</span><span>&nbsp;</span>
                        <span>offer(s) placed</span>
                    </caption>
                    <thead>
                        <tr>
                            <th class="col-2">Username</th>
                            <th class="col-1">Price</th>
                            <th class="col-1">Quantity</th>
                            <th class="col-2">Delivery Date</th>
                            <th class="col-1">&nbsp;</th>
                        </tr>
                    </thead>
                    <tbody>
                        <OfferTableRow v-for="offer in offers" :key="offer.id" :offer="offer" />
                    </tbody>
                </table>
            </div>
            <div class="text-center">
                <button class="btn btn-secondary buttonSpacer" @click="cancel">Cancel</button>
            </div>
        </div>
    </div>
</template>

<script>
import http from '@/http'
import AuctionCard from '@/components/cards/AuctionCard.vue';
import OfferTableRow from '@/components/table/OfferTableRow.vue';

export default {
    name: "OfferView",
    data: () => ({
        auction: {},
        offers: [],
    }),
    beforeMount() {
        this.auction = this.$store.state.auction;
        this.requestOffers();
    },
    mounted() {
        window.event.on("reloadOffers", () => {
            this.requestOffers()
        });
    },
    unmounted() {
        window.event.all.delete("reloadOffers");
    },
    methods: {
        async requestOffers() {
            try {
                const response = await http.get("auctions/" + this.auction.id + "/offers", {
                    headers: {
                        "Authorization": `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                });
                this.offers = response.data;
            }
            catch (error) {
                console.log(error);
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                };
                window.event.emit("showErrorModal", modalData);
            }
        },
        cancel() {
            this.$router.push("/myAuctions");
        }
    },
    components: { OfferTableRow, AuctionCard }
}
</script>

<style>
.centerContent {
    justify-content: center;
}
</style>