<template>
    <div class="card">
        <div :id="id" class="carousel slide card-image-area" data-ride="carousel">
            <ol v-if="product.images.length > 1" class="carousel-indicators">
                <CarouselIndicator 
                    v-for="(image, counter) in product.images"
                    :key="image.id"
                    :to="counter"
                    :target="this.hid"
                    v-bind:class="{ active: (counter == 0) }"
                />
            </ol>
            <div class="carousel-inner carousel-item-area">
                <CarouselItem 
                    v-for="(image, counter) in product.images"
                    :key="image.id"
                    :image="image"
                    :alt="'Slide ' + (counter + 1)"
                    v-bind:class="{ active: (counter == 0) }"
                />
                <CarouselItem 
                    v-if="product.images.length == 0"
                    :key="99999"
                    :image="{ id: '99999', file: 'NoImage.jpg' }"
                    :alt="'No image found.'"
                    v-bind:class="{ active: (true) }"
                />
            </div>
            <a v-if="product.images.length > 1" class="carousel-control-prev" :href="hid" role="button" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <!-- <span class="sr-only">Previous</span> -->
            </a>
            <a v-if="product.images.length > 1" class="carousel-control-next" :href="hid" role="button" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <!-- <span class="sr-only">Next</span> -->
            </a>
        </div>
        <div class="card-body">
            <h5 class="card-title">{{product.name}}</h5>
            <p class="card-text">{{product.description}}</p>
        </div>
        <div class="card-body auctionLink">
            <a href="#" class="card-link">Create Auction</a>
        </div>
    </div>
</template>

<script>
import CarouselIndicator from '../atoms/CarouselIndicator.vue';
import CarouselItem from '../organisms/CarouselItem.vue';

export default {
    name: "ProductCard",
    props: ["product"],
    data: () => ({
        id: "",
        hid: ""
    }),
    mounted() {
        this.id = "id" + this.product.id
        this.hid = "#" + this.id
    },
    components: { CarouselItem, CarouselIndicator }
}
</script>

<style>
.auctionLink {
    padding-top: 0;
}

.card-image-area {
    align-self: center;
}

.carousel-control-prev-icon,
.carousel-control-next-icon {
  width: 100%;
  outline: black;
  background-image: none;
}

.carousel-control-next-icon:after
{
  content: '>';
  font-size: 25px;
  color: black;
}

.carousel-control-prev-icon:after {
  content: '<';
  font-size: 25px;
  color: black;
}

.carousel-control-prev,
.carousel-control-next {
    color: rgba(0, 0, 0, 0) !important;
}

.card {
    margin: 10px;
    min-width: 18rem;
    max-width: 18rem;
    padding: 10px;
}

.carousel-item-area {
    width: 266px;
    height: 238.8px;
}
</style>