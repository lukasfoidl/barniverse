<template>
    <div class="row justify-content-center align-items-center">
        <div class="col-xs-12 col-sm-11 col-md-10 col-lg-8 col-xl-6">
            <div class="table-responsive">
                <table class="table table-hover table-striped">
                    <caption>
                        <span>{{ offers.length }}</span><span>&nbsp;</span>
                        <span>offer(s) placed</span>
                    </caption>
                    <thead>
                        <tr>
                            <th class="col-1">Offer</th>
                            <th class="col-2">Auction</th>
                            <th class="col-2">Product</th>
                            <th class="col-1">Price</th>
                            <th class="col-1">Quantity</th>
                            <th class="col-2">Delivery Date</th>
                            <th class="col-1">&nbsp;</th>
                        </tr>
                    </thead>
                    <tbody>
                        <OfferTableRow v-for="offer in offers" :key="offer.id" :offer="offer" :isMyOffersView="true" />
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<script>
import http from '@/http'
import OfferTableRow from '@/components/table/OfferTableRow.vue';

export default {
    name: "MyOffersView",
    data: () => ({
        offers: [],
    }),
    beforeMount() {
        this.requestOffers();
    },
    methods: {
        async requestOffers() {
            try {
                const response = await http.get("users/" + this.$store.state.uuid + "/offers", {
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
    },
    components: { OfferTableRow }
}
</script>

<style>
</style>