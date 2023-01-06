<template>
    <div class="card col-xs-12 col-sm-10 col-md-5 col-lg-3">
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
            <h5 class="card-title text-truncate">{{product.name}}</h5>
            <p class="card-text text-truncate-custom">{{product.description}}</p>
        </div>
        <div class="card-body auctionLink bottom-area">
            <Popover placement="right" :hover="true">
                <i class="bi bi-info-circle icon" alt="Details"></i>
                <template #content>
                    <div class="popover card-body">
                        <h5 class="card-title">{{product.name}}</h5>
                        <p class="card-text">{{product.description}}</p>
                    </div>
                </template>
            </Popover>
            <i v-if="isAdmin" class="bi bi-pencil-fill editStyle" alt="Update product" @click="navigateToProductUpdateView"></i>
            <a href="#" class="card-link ms-auto">Create Auction</a>
        </div>
    </div>
</template>

<script>
import CarouselIndicator from '@/components/atoms/CarouselIndicator.vue';
import CarouselItem from '@/components/molecules/CarouselItem.vue';
import Popover from "vue3-popper"

export default {
    name: "ProductCard",
    props: ["product"],
    data: () => ({
        id: "",
        hid: "",
    }),
    mounted() {
        this.id = "id" + this.product.id
        this.hid = "#" + this.id
    },
    components: { CarouselItem, CarouselIndicator, Popover },
    methods: {
        navigateToProductUpdateView() {
            this.$store.commit("saveProduct", { product: this.product })
            this.$router.push("/products/update")
        }
    },
    computed: {
        isAdmin() {
            return this.$store.getters.isAdmin
        }
    }
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
    padding: 10px;
    height: 398px;
}

.carousel-item-area {
    width: 200px;
    height: 200px;
}

.bottom-area {
    flex: none;
    display: flex;
}

.text-truncate-custom {
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical; 
    overflow: hidden; 
}

.icon {
    color:black
}

.popover {
    --popper-theme-background-color: #ffffff;
    --popper-theme-background-color-hover: #ffffff;
    --popper-theme-text-color: #333333;
    --popper-theme-border-width: 1px;
    --popper-theme-border-style: solid;
    --popper-theme-border-color: #eeeeee;
    --popper-theme-border-radius: 6px;
    --popper-theme-padding: 32px;
    --popper-theme-box-shadow: 0 6px 30px -6px rgba(0, 0, 0, 0.25);
}

.editStyle, .editStyle:hover {
    color: black;
    margin-left: 10px;
    cursor: pointer;
}
</style>