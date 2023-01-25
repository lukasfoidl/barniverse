<template>
    <div class="justify-content-center d-flex">
        <Taskbar />
    </div>
    <div class="justify-content-center">
        <div class="row justify-content-center">
            <ProductCard v-for="product in products" :key="product.id" :product="product" />
        </div>
    </div>
</template>

<script>
import ProductCard from "@/components/cards/ProductCard.vue";
import Taskbar from "@/components/molecules/Taskbar.vue";
import http from "@/http"

export default {
    name: "ProductView",
    data: () => ({
        products: []
    }),
    components: { ProductCard, Taskbar },
    methods: {
        async requestProducts() {
            try {
                const response = await http.get("products")
                this.products = response.data
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
        this.requestProducts();
    },
}
</script>

<style>
</style>