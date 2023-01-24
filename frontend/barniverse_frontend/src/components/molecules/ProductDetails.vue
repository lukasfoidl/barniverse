<template>
    <div :id="id" class="carousel slide justify-content-center d-flex" data-ride="carousel">
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
        <h5 :id="'productDetailsTitle' + product.id" class="card-title text-truncate">{{product.title}}</h5>
        <div>
            <span :id="'productDetailsDescription' + product.id" class="text-truncate-custom">{{ product.description }}</span>
            <TitleDescriptionPopover v-if="isTruncatedTitle || isTruncatedDescription" :object="product" />
        </div>
    </div>
</template>

<script>
import CarouselIndicator from '@/components/atoms/CarouselIndicator.vue';
import CarouselItem from '@/components/molecules/CarouselItem.vue';
import TitleDescriptionPopover from '../molecules/TitleDescriptionPopover.vue';

export default {
    name: "ProductDetails",
    props: ["product", "extraId"],
    data: () => ({
        id: "",
        hid: "",
        isTruncatedTitle: false,
        isTruncatedDescription: false,
    }),
    mounted() {
        this.id = "id" + this.extraId + this.product.id
        this.hid = "#" + this.id

        window.addEventListener("resize", this.UpdateTruncation);
        this.UpdateTruncation();
    },
    unmounted() {
        window.removeEventListener("resize", this.UpdateTruncation);
    },
    methods: {
        UpdateTruncation() {
            var element = window.$("#productDetailsTitle" + this.product.id)[0]
            this.isTruncatedTitle = element.offsetWidth < element.scrollWidth
            element = window.$("#productDetailsDescription" + this.product.id)[0]
            this.isTruncatedDescription = element.offsetHeight < element.scrollHeight
        }
    },
    components: { CarouselItem, CarouselIndicator, TitleDescriptionPopover },
}
</script>

<style>
.card-body {
    padding-bottom: 0px;
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

.carousel-item-area {
    width: 200px;
    height: 200px;
}

.icon {
    color:black
}

.text-truncate-custom {
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical; 
    overflow: hidden; 
}
</style>