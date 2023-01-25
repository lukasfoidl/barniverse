<template>
    <!-- OfferView -->
    <tr v-if="!isMyOffersView">
        <td>{{ offer.user.username }}</td>
        <td :class="{'outOfRange': (!isInPriceRange())}">{{ offer.price }}</td>
        <td :class="{'outOfRange': (!isInQuantityRange())}">{{ offer.quantity }}</td>
        <td :class="{'outOfRange': (!isInDateRange())}">
            <span>{{ new Date(offer.deliveryDate).toLocaleDateString('de-AT') }}</span><span>&nbsp;</span>
            <span>{{ new Date(offer.deliveryDate).toLocaleTimeString('de-AT') }}</span>
        </td>
        <td>
            <i v-if="isOfferRunning()" class="bi bi-check-square-fill" v-bind:class="{ 
                'clickableInAuctionRange': (isAuctionFinished() && isInAuctionRange()),
                'clickableOutAuctionRange': (isAuctionFinished() && !isInAuctionRange()),
                'unclickable': (!isAuctionFinished()) }" alt="Accept offer" @click="acceptOffer" />
            <i v-if="isOfferAccepted()" class="bi bi-patch-check-fill acceptedColor" alt="Offer accepted" />
        </td>
    </tr>

    <!-- MyOffersView -->
    <tr v-if="isMyOffersView">
        <td> 
            <i v-if="!isAuctionLocked()" class="bi bi-file-earmark-text standardColor pointer" alt="View offer" @click="navigateToOfferDetailsView"/>
        </td>
        <td>{{ offer.auction.title }}</td>
        <td>{{ offer.auction.product.title }}</td>
        <td :class="{'outOfRange': (!isInPriceRange())}">{{ offer.price }}</td>
        <td :class="{'outOfRange': (!isInQuantityRange())}">{{ offer.quantity }}</td>
        <td :class="{'outOfRange': (!isInDateRange())}">
            <span>{{ new Date(offer.deliveryDate).toLocaleDateString('de-AT') }}</span><span>&nbsp;</span>
            <span>{{ new Date(offer.deliveryDate).toLocaleTimeString('de-AT') }}</span>
        </td>
        <td>
            <i v-if="!isAuctionLocked() && isOfferRunning()" class="bi bi-stopwatch-fill runningColor" alt="Offer still running" />
            <i v-if="!isAuctionLocked() && isOfferRejected()" class="bi bi-x-circle-fill rejectedColor" alt="Offer still running" />
            <i v-if="!isAuctionLocked() && isOfferAccepted()" class="bi bi-patch-check-fill acceptedColor" alt="Offer accepted" />
            <i v-if="isAuctionLocked()" class="bi bi-lock-fill" alt="Auction Locked" />
        </td>
    </tr>
</template>

<script>
import http from '@/http'

export default {
    name: "OfferTableRow",
    props: ["offer", "isMyOffersView"],
    methods: {
        async acceptOffer() {
            try {
                const response = await http.put("/offers/" + this.offer.id + "/accept", null, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                window.event.emit("reloadOffers");
                const modalData = {
                    title: "Info (" + response.status + ")",
                    text: "Offer accepted successfully!"
                }
                window.event.emit("showErrorModal", modalData);
            } catch(error) {
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
        },
        isAuctionLocked() {
            return this.offer.auction.state == "locked";
        },
        isAuctionFinished() {
            return new Date(this.offer.auction.endDate) < new Date()
        },
        isInAuctionRange() {
            return this.isInPriceRange() && this.isInQuantityRange() && this.isInDateRange()
        },
        isInPriceRange() {
            return this.offer.price >= this.offer.auction.minPrice &&
                this.offer.price <= this.offer.auction.maxPrice
        },
        isInQuantityRange() {
            return this.offer.quantity >= this.offer.auction.minQuantity &&
                this.offer.quantity <= this.offer.auction.maxQuantity
        },
        isInDateRange() {
            return new Date(this.offer.deliveryDate) >= new Date(this.offer.auction.startDeliveryDate) &&
                new Date(this.offer.deliveryDate) <= new Date(this.offer.auction.endDeliveryDate)
        },
        isOfferRunning() {
            return this.offer.state == "running"
        },
        isOfferAccepted() {
            return this.offer.state == "accepted"
        },
        isOfferRejected() {
            return this.offer.state == "rejected"
        },
        navigateToOfferDetailsView() {
            this.$store.commit("saveOffer", { offer: this.offer });
            this.$router.push("/offers/details");
        }
    }
}
</script>

<style>
.clickableInAuctionRange {
    cursor: pointer;
    color: green;
}

.clickableOutAuctionRange {
    cursor: pointer;
    color: orange;
}

.outOfRange {
    color: orange !important;
}

.acceptedColor {
    color: green;
}
.rejectedColor {
    color: red;
}
.runningColor {
    color: orange;
}
.standardColor {
    color: black;
}

.unclickable {
    pointer-events: none;
    color: lightgray;
}

.pointer {
    cursor: pointer;
}
</style>