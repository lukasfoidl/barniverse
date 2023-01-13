<template>
    <img :src="this.imageURL" :alt="this.alt">
</template>

<script>
import http from "@/http"

export default {
    name: "Image",
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
</style>