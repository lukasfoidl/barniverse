<template>
    <div class="carousel-item box-size">
        <Image class="d-block w-100 productImage box-size" :key="this.image.id" :image="this.image" :alt="this.alt" />
    </div>
</template>

<script>
import Image from "@/components/atoms/Image.vue"

export default {
    name: "CarouselItem",
    props: ["image", "alt"],
    components: { Image }
}
</script>

<style>
.productImage {
    border-radius: 0.375rem;
}

.box-size {
    object-fit: cover;
    width: 100%;
    height: 100%;
}
</style>