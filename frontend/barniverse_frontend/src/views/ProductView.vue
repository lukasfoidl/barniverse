<template>
    <div id="cardList" class="cardList centerContent">
        <div class="row centerContent">
            <ProductCard v-for="product in products" :key="product.id" :product="product"/>
        </div>
    </div>
</template>

<script>
import ProductCard from "../components/molecules/ProductCard.vue";

export default {
    name: "ProductView",
    data: () => ({
        products: [
            { id: "12345", name: "Wine", description: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam", images: [
                    { id: "98765", file: "images/sample_wine.jpeg" },
                    { id: "87654", file: "images/sample_beer.jpg" }
                ] },
            { id: "23456", name: "Beer", description: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam, Lorem ipsum dolor sit amet, consetetur.", images: [
                    { id: "76543", file: "images/sample_wine.jpeg" },
                    { id: "65432", file: "images/sample_beer.jpg" },
                    { id: "54321", file: "images/sample_wine.jpeg" },
                    { id: "43210", file: "images/sample_whiskey.jpg" }
                ] },
            { id: "34567", name: "Whiskey 1", description: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam, Lorem ipsum dolor sit amet, consetetur, Lorem ipsum dolor sit amet, consetetur.", images: [] },
            { id: "45678", name: "Whiskey 2", description: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam, Lorem ipsum dolor sit amet, consetetur, Lorem ipsum dolor sit amet, consetetur.", images: [] },
            { id: "56789", name: "Whiskey 3", description: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam, Lorem ipsum dolor sit amet, consetetur, Lorem ipsum dolor sit amet, consetetur.", images: [] },
            { id: "67890", name: "Whiskey 4", description: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam, Lorem ipsum dolor sit amet, consetetur, Lorem ipsum dolor sit amet, consetetur.", images: [] },
            { id: "78901", name: "Whiskey 5", description: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam, Lorem ipsum dolor sit amet, consetetur, Lorem ipsum dolor sit amet, consetetur.", images: [] },
            { id: "89012", name: "Whiskey 6", description: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam, Lorem ipsum dolor sit amet, consetetur, Lorem ipsum dolor sit amet, consetetur.", images: [] },
            { id: "90123", name: "Whiskey 7", description: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam, Lorem ipsum dolor sit amet, consetetur, Lorem ipsum dolor sit amet, consetetur.", images: [] },
            { id: "01234", name: "Whiskey 8", description: "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam, Lorem ipsum dolor sit amet, consetetur, Lorem ipsum dolor sit amet, consetetur.", images: [] }
        ]
    }),
    components: { ProductCard }
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