<template>
    <div id="cardList" class="cardList centerContent">
        <div class="row centerContent">
            <ProductCard v-for="product in products" :key="product.id" :product="product"/>
        </div>
    </div>
</template>

<script>
import ProductCard from "../components/molecules/ProductCard.vue";
import http from "../http"

export default {
    name: "ProductView",
    data: () => ({
        products: []
    }),
    components: { ProductCard },
    methods: {
        async requestProducts() {
            try {
                const response = await http.get("products")
                this.products = response.data
            } catch(error) {
                console.log(error)
                const data = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", data);
            }

        }
    },
    beforeMount() {
        this.requestProducts();
    },
}
</script>

<style>
.cardList {
    display: flex;
    flex-direction: row;
}

.centerContent {
    justify-content: center;
}
</style>