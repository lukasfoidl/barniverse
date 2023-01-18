<template>
    <div class="card col-xs-12 col-sm-12 col-md-10 col-lg-5">
        <div class="badgeContainer ms-auto">
            <span v-if="isAuctionActive() && isBeforeAuctionStart()" class="badge rounded-pill text-bg-success">Soon</span>
            <span v-if="isAuctionActive() && isAuctionRunning()" class="badge rounded-pill text-bg-warning">Bid now</span>
            <span v-if="isAuctionActive() && isAuctionFinished()" class="badge rounded-pill text-bg-danger">Closed</span>
            <span v-if="isAuctionLocked()" class="badge rounded-pill text-bg-dark">Locked</span>
        </div>
        <div class="row flexer">
            <div class="col-xs-12 col-sm-6">
                <ProductDetails :product="auctionData.product" :extraId="auctionData.id" />
            </div>
            <div class="col-xs-12 col-sm-6">
                <div class="card-body">
                    <h4 :id="'auctionDetailsTitle' + auctionData.id" class="text-truncate">{{ auctionData.title }}</h4>
                    <div>
                        <span :id="'auctionDetailsDescription' + auctionData.id" class="text-truncate-custom">{{ auctionData.description }}</span>
                        <TitleDescriptionPopover v-if="isTruncatedTitle || isTruncatedDescription" :object="auctionData" />
                    </div>
                    <div>
                        <span><i class="bi bi-currency-euro" alt="Price"></i>&nbsp;&nbsp;</span>
                        <span>{{ auctionData.minPrice }}</span>
                        <span> - </span>
                        <span>{{ auctionData.maxPrice }}</span>
                    </div>
                    <div>
                        <span><i class="bi bi-puzzle" alt="Quantity"></i>&nbsp;&nbsp;</span>
                        <span>{{ auctionData.minQuantity }}</span>
                        <span> - </span>
                        <span>{{ auctionData.maxQuantity }}</span>
                    </div>
                    <div>
                        <span><i class="bi bi-hourglass-split" alt="Auction end date"></i>&nbsp;&nbsp;</span>
                        <span>{{ new Date(auctionData.endDate).toLocaleDateString('de-AT') }}</span><span>&nbsp;</span>
                        <span>{{ new Date(auctionData.endDate).toLocaleTimeString('de-AT') }}</span>
                    </div>
                    <div>
                        <span><i class="bi bi-truck" alt="Start delivery date"></i>&nbsp;&nbsp;</span>
                        <span>{{ new Date(auctionData.startDeliveryDate).toLocaleDateString('de-AT') }}</span><span>&nbsp;</span>
                        <span>{{ new Date(auctionData.startDeliveryDate).toLocaleTimeString('de-AT') }}</span>
                    </div>
                    <div>
                        <span><i class="bi bi-dash" alt="End delivery date"></i>&nbsp;&nbsp;</span>
                        <span>{{ new Date(auctionData.endDeliveryDate).toLocaleDateString('de-AT') }}</span><span>&nbsp;</span>
                        <span>{{ new Date(auctionData.endDeliveryDate).toLocaleTimeString('de-AT') }}</span>
                    </div>
                    <div>
                        <span><i class="bi bi-person" alt="Auction owner"></i>&nbsp;&nbsp;</span>
                        <span>{{ auctionData.user.username }}</span>
                    </div>
                </div>
            </div>
        </div>
        <div v-if="isAdmin || isUser" class="card-body bottom-area">
            <i v-if="isAuctionActive() && isBeforeAuctionStart() && isOwnAuction" class="bi bi-pencil-fill pointer spaceToOtherIcons" alt="Update auction"
                @click="navigateToAuctionUpdateView" />
            <i v-if="isAuctionActive() && isOwnAuction && !isBeforeAuctionStart()" v-bind:class="{ 'bi-file-earmark-text-fill': (isAuctionRunning()), 'bi-file-earmark-check-fill': (isAuctionFinished()) }"
                class="bi pointer spaceToOtherIcons" alt="Show Offers" @click="navigateToOfferView" />
            <i v-if="isAdmin && !isAuctionFinished()" v-bind:class="{ 'bi-lock-fill': (isAuctionActive()), 'bi-unlock-fill': (isAuctionLocked()) }"
                class="bi pointer" alt="Lock Auction" @click="lockAuction" />
            <a v-if="isAuctionActive() && !isOwnAuction && isAuctionRunning()" class="card-link ms-auto pointer" @click="navigateToOfferCreateView">Create Offer</a>
        </div>
    </div>
</template>

<script>
import http from "@/http"
import ProductDetails from '../molecules/ProductDetails.vue';
import TitleDescriptionPopover from '../molecules/TitleDescriptionPopover.vue';

export default {
    name: "AuctionCard",
    props: ["auction"],
    data: () => ({
        auctionData: {},
        id: "",
        hid: "",
        isTruncatedTitle: false,
        isTruncatedDescription: false,
    }),
    beforeMount() {
        this.auctionData = this.auction
    },
    mounted() {
        this.id = "id" + this.auctionData.id + this.auctionData.product.id
        this.hid = "#" + this.id
        
        window.addEventListener("resize", this.UpdateTruncation);
        this.UpdateTruncation();
    },
    unmounted() {
        window.removeEventListener("resize", this.UpdateTruncation);
    },
    components: { TitleDescriptionPopover, ProductDetails },
    methods: {
        navigateToAuctionUpdateView() {
            this.$store.commit("saveAuction", { auction: this.auctionData })
            this.$router.push("/auctions/update")
        },
        async lockAuction() {
            try {
                const response = await http.put("/auctions/toggleState/" + this.auctionData.id, null, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                this.auctionData.state = response.data
                const modalData = {
                    title: "Info (" + response.status + ")",
                    text: "Auction state of " + this.auctionData.title + " updated successfully!"
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
        UpdateTruncation() {
            var element = window.$("#auctionDetailsTitle" + this.auctionData.id)[0]
            this.isTruncatedTitle = element.offsetWidth < element.scrollWidth
            element = window.$("#auctionDetailsDescription" + this.auctionData.id)[0]
            this.isTruncatedDescription = element.offsetHeight < element.scrollHeight
        },
        isBeforeAuctionStart() {
            return new Date(this.auctionData.startDate) > new Date()
        },
        isAuctionFinished() {
            return new Date(this.auctionData.endDate) < new Date()
        },
        isAuctionRunning() {
            return (!this.isBeforeAuctionStart()) && (!this.isAuctionFinished())
        },
        isAuctionActive() {
            return this.auctionData.state == "active"
        },
        isAuctionLocked() {
            return this.auctionData.state == "locked"
        }
    },
    computed: {
        isAdmin() {
            return this.$store.getters.isAdmin
        },
        isUser() {
            return this.$store.getters.isUser
        },
        isOwnAuction() {
            return this.auctionData.user.id == this.$store.state.uuid
        },
    }
}
</script>

<style>
.badgeContainer {
    padding-right: 60px;
}

.badge {
    width: 60px;
    position: absolute;
}

.card {
    margin: 10px;
    padding: 10px;
}

.card-body {
    padding-bottom: 0px;
}

.bottom-area {
    padding-top: 1rem;
    padding-bottom: 0px;
    flex: none;
    display: flex;
}

.text-truncate-custom {
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical; 
    overflow: hidden; 
}

.pointer {
    cursor: pointer;
}

.flexer {
    flex: 1 1 auto;
}

.spaceToOtherIcons {
    margin-right: 10px;
}

.removeBottomPadding {
    padding-bottom: 0px;
}

.bi-file-earmark-text-fill {
    color: orange;
}

.bi-file-earmark-check-fill {
    color: green;
}
</style>