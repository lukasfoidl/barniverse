<template>
    <div class="card" v-bind:class="{ 'col-xs-12 col-sm-10 col-md-5 col-lg-3': (!isSingleView), 'cardBottomSpacer': (isSingleView) }">
        <ProductDetails :product="product" />

        <div v-if="(isAdmin || isUser) && !isSingleView" class="card-body bottom-area">
            <i v-if="isAdmin" class="bi bi-pencil-fill editStyle pointer" alt="Update product" @click="navigateToProductUpdateView"></i>
            <a class="card-link ms-auto pointer" @click="navigateToAuctionCreateView">Create Auction</a>
        </div>
    </div>
</template>

<script>
import ProductDetails from '../molecules/ProductDetails.vue'

export default {
    name: "ProductCard",
    props: ["product", "isSingleView"],
    methods: {
        navigateToProductUpdateView() {
            this.$store.commit("saveProduct", { product: this.product });
            this.$router.push("/products/update");
        },
        navigateToAuctionCreateView() {
            this.$store.commit("saveProduct", { product: this.product });
            this.$router.push("/auctions/create");
        },
    },
    computed: {
        isAdmin() {
            return this.$store.getters.isAdmin;
        },
        isUser() {
            return this.$store.getters.isUser;
        }
    },
    components: { ProductDetails }
}
</script>

<style>
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

.editStyle, .editStyle:hover {
    color: black;
}

.pointer {
    cursor: pointer;
}

.cardBottomSpacer {
    margin-bottom: 10px !important;
    margin: 0px;
}
</style>