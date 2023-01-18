<template>
    <div class="centerContent">
        <div class="row centerContent">
            <AuctionCard v-for="auction in auctions" :key="auction.id" :auction="auction" />
        </div>
    </div>
</template>

<script>
import AuctionCard from "@/components/cards/AuctionCard.vue";
import http from "@/http"

export default {
    name: "AuctionView",
    data: () => ({
        auctions: []
    }),
    components: { AuctionCard },
    methods: {
        async requestAuctions() {
            try {
                const response = await http.get("auctions")
                this.auctions = response.data
            } catch (error) {
                console.log(error)
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
        },
    },
    beforeMount() {
        this.requestAuctions();
    },
}
</script>

<style>
.centerContent {
    justify-content: center;
}
</style>