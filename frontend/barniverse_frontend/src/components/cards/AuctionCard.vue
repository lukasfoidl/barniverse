<template>
    <div class="card col-xs-12 col-sm-12 col-md-10 col-lg-5">
        <div class="badgeContainer ms-auto">
            <span v-if="isBeforeAuctionStart()" class="badge rounded-pill text-bg-success">Soon</span>
            <span v-if="isAuctionRunning()" class="badge rounded-pill text-bg-warning">Bid now</span>
            <span v-if="isAuctionFinished()" class="badge rounded-pill text-bg-danger">Closed</span>
        </div>
        <div class="row flexer">
            <div class="col-xs-12 col-sm-6">
                <ProductDetails :product="auction.product" :extraId="auction.id" />
            </div>
            <div class="col-xs-12 col-sm-6">
                <div class="card-body">
                    <h4 :id="'auctionDetailsTitle' + auction.id" class="text-truncate">{{ auction.title }}</h4>
                    <div>
                        <span :id="'auctionDetailsDescription' + auction.id" class="text-truncate-custom">{{ auction.description }}</span>
                        <TitleDescriptionPopover v-if="isTruncatedTitle || isTruncatedDescription" :object="auction" />
                    </div>
                    <div>
                        <span><i class="bi bi-currency-euro" alt="Price"></i>&nbsp;&nbsp;</span>
                        <span>{{ auction.minPrice }}</span>
                        <span> - </span>
                        <span>{{ auction.maxPrice }}</span>
                    </div>
                    <div>
                        <span><i class="bi bi-puzzle" alt="Quantity"></i>&nbsp;&nbsp;</span>
                        <span>{{ auction.minQuantity }}</span>
                        <span> - </span>
                        <span>{{ auction.maxQuantity }}</span>
                    </div>
                    <div>
                        <span><i class="bi bi-hourglass-split" alt="Auction end date"></i>&nbsp;&nbsp;</span>
                        <span>{{ new Date(auction.startDate).toLocaleDateString('de-AT') }}</span><span>&nbsp;</span>
                        <span>{{ new Date(auction.startDate).toLocaleTimeString('de-AT') }}</span>
                    </div>
                    <div>
                        <span><i class="bi bi-truck" alt="Start delivery date"></i>&nbsp;&nbsp;</span>
                        <span>{{ new Date(auction.endDate).toLocaleDateString('de-AT') }}</span><span>&nbsp;</span>
                        <span>{{ new Date(auction.endDate).toLocaleTimeString('de-AT') }}</span>
                    </div>
                    <!-- <div>
                        <span><i class="bi bi-dash" alt="End delivery date"></i>&nbsp;&nbsp;</span>
                        <span>{{ new Date(auction.endDeliveryDate).toLocaleDateString('de-AT') }}</span><span>&nbsp;</span>
                        <span>{{ new Date(auction.endDeliveryDate).toLocaleTimeString('de-AT') }}</span>
                    </div> -->
                    <div>
                        <span><i class="bi bi-person" alt="Auction owner"></i>&nbsp;&nbsp;</span>
                        <span>{{ auction.user.username }}</span>
                    </div>
                </div>
            </div>
        </div>
        <div v-if="isAdmin || isUser" class="card-body bottom-area">
            <i v-if="isBeforeAuctionStart() && isOwnAuction" class="bi bi-pencil-fill pointer spaceToOtherIcons" alt="Update auction"
                @click="navigateToAuctionUpdateView"></i>
            <i v-if="isOwnAuction" v-bind:class="{ 'bi-file-earmark-text-fill': (isAuctionRunning()), 'bi-file-earmark-check-fill': (isAuctionFinished()) }" class="bi pointer" alt="Show Offers"
                @click="navigateToOfferView"></i>
            <a v-if="(!isOwnAuction) && isAuctionRunning()" class="card-link ms-auto pointer" @click="navigateToOfferCreateView">Create Offer</a>
        </div>
    </div>
</template>

<script>
import ProductDetails from '../molecules/ProductDetails.vue';
import TitleDescriptionPopover from '../molecules/TitleDescriptionPopover.vue';

export default {
    name: "AuctionCard",
    props: ["auction"],
    data: () => ({
        id: "",
        hid: "",
        isTruncatedTitle: false,
        isTruncatedDescription: false,
    }),
    mounted() {
        this.id = "id" + this.auction.id + this.auction.product.id
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
            this.$store.commit("saveAuction", { auction: this.auction })
            this.$router.push("/auctions/update")
        },
        // navigateToAuctionCreateView() {
        //     this.$store.commit("saveProduct", { product: this.product })
        //     this.$router.push("/auctions/create")
        // }
        UpdateTruncation() {
            var element = window.$("#auctionDetailsTitle" + this.auction.id)[0]
            this.isTruncatedTitle = element.offsetWidth < element.scrollWidth
            element = window.$("#auctionDetailsDescription" + this.auction.id)[0]
            this.isTruncatedDescription = element.offsetHeight < element.scrollHeight
        },
        isBeforeAuctionStart() {
            return new Date(this.auction.startDate) > new Date()
        },
        isAuctionFinished() {
            return new Date(this.auction.endDate) < new Date()
        },
        isAuctionRunning() {
            return (!this.isBeforeAuctionStart()) && (!this.isAuctionFinished())
        },
    },
    computed: {
        isAdmin() {
            return this.$store.getters.isAdmin
        },
        isUser() {
            return this.$store.getters.isUser
        },
        isOwnAuction() {
            return this.auction.user.id == this.$store.state.uuid
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