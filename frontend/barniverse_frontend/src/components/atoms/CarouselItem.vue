<template>
    <div class="carousel-item box-size">
        <img class="d-block w-100 productImage box-size" :src="require(`@/${this.image.file}`)" :alt="this.alt">
    </div>
</template>

<script>
export default {
    name: "CarouselItem",
    props: ["image", "alt"],
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