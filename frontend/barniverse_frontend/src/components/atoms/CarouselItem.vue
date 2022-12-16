<template>
    <div class="carousel-item box-size">
        <img class="d-block w-100 productImage box-size" :src="this.imageURL" :alt="this.alt">
    </div>
</template>

<script>
import http from "../../http"

export default {
    name: "CarouselItem",
    props: ["image", "alt"],
    data: () => ({
        imageURL: "ImageNotFound.jpg"
    }),
    methods: {
        async requestImage() {
            try {
                const response = await http.get("images/" + this.image.file, { responseType: "blob" })
                this.imageURL = window.URL.createObjectURL(new Blob([response.data], { type: "image/jpg" }));
            } catch(error) {
                console.error(error)
            }
        }
    },
    mounted() {
        this.requestImage();
    },
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