<template>
    <div class="d-flex">
        <FilterBar class="ms-auto" :filters="filters"/>
    </div>
    <div class="justify-content-center">
        <div class="row justify-content-center">
            <AuctionCard v-for="auction in displayAuctions" :key="auction.id" :auction="auction" />
        </div>
    </div>
</template>

<script>
import AuctionCard from "@/components/cards/AuctionCard.vue";
import FilterBar from "@/components/molecules/FilterBar.vue";
import http from "@/http"

export default {
    name: "AuctionView",
    data: () => ({
        filters: ["Soon", "Open", "Closed", "Locked"],
        allAuctions: [],
        displayAuctions: []
    }),
    components: { AuctionCard, FilterBar },
    mounted() {
        window.event.on("filterAuctions", (data) => {
            this.filterAuctions(data.filter)
        });
    },
    unmounted() {
        window.event.all.delete("filterAuctions");
    },
    methods: {
        async requestAuctions() {
            try {
                var response;
                if (this.$store.getters.isAdmin) {
                    response = await http.get("auctions", {
                        headers: {
                            'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                        }
                    })
                } else {
                    response = await http.get("auctions/unlocked")
                }
                this.allAuctions = response.data
                this.filterAuctions(this.filters[1])
            } catch (error) {
                console.log(error)
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
        },
        isBeforeAuctionStart(auction) {
            return new Date(auction.startDate) > new Date()
        },
        isAuctionFinished(auction) {
            return new Date(auction.endDate) < new Date()
        },
        isAuctionRunning(auction) {
            return (!this.isBeforeAuctionStart(auction)) && (!this.isAuctionFinished(auction))
        },
        isAuctionLocked(auction) {
            return auction.state == "locked"
        },
        filterAuctions(filter) {
            if (filter == this.filters[0]) { // Soon
                this.displayAuctions = this.allAuctions.filter(auction => this.isBeforeAuctionStart(auction) && !this.isAuctionLocked(auction))
            }
            else if (filter == this.filters[1]) { // Open
                this.displayAuctions = this.allAuctions.filter(auction => this.isAuctionRunning(auction) && !this.isAuctionLocked(auction))
            }
            else if (filter == this.filters[2]) { // Closed
                this.displayAuctions = this.allAuctions.filter(auction => this.isAuctionFinished(auction) && !this.isAuctionLocked(auction))
            }
            else if (filter == this.filters[3]) { // Locked
                this.displayAuctions = this.allAuctions.filter(auction => this.isAuctionLocked(auction))
            }
            else {
                this.displayAuctions = []
            }
        }
    },
    beforeMount() {
        this.requestAuctions();
    },
}
</script>

<style>
</style>