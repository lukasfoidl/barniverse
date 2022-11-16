<template>
    <li class="" :data-bs-slide-to="this.to" :data-bs-target="this.target"></li>
</template>

<script>
export default {
    name: "CarouselIndicator",
    props: ["to", "target"]
}
</script>

<style>
</style>