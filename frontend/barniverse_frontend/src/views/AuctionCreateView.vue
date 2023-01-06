<template>
    <div class="row justify-content-center align-items-center">
        <div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-4">

            <span class="badge rounded-pill text-bg-warning">In Development</span>

            <AuctionForm :trigger="trigger" :auction="auction"/>

            <div class="text-center">
                <button class="btn btn-primary buttonSpacer" @click="triggerValidation">Save</button>
                <button class="btn btn-secondary buttonSpacer" @click="cancel">Cancel</button>
            </div>
        </div>
    </div>
</template>

<script>
import http from "@/http"
import AuctionForm from '@/components/forms/AuctionForm.vue';

export default {
    name: "AuctionCreateView",
    components: { AuctionForm },
    data: () => ({
        auction: {
            id: undefined,
            title: "",
            description: ""
        },
        trigger: false,
    }),
    mounted() {
        window.event.on("validationCompleted", (data) => {
            this.createAuction(data)
        });
    },
    unmounted() {
        window.event.all.delete("validationCompleted");
    },
    methods: {
        triggerValidation() {
            this.trigger = !this.trigger
        },
        async createAuction(data) {
            try {
                const requestBody = {
                    title: data.title,
                    description: data.description,
                    minPrice: data.minPrice,
                    maxPrice: data.maxPrice,
                    minQuantity: data.minQuantity,
                    maxQuantity: data.maxQuantity,
                    startDeliveryDate: data.startDeliveryDate,
                    endDeliveryDate: data.endDeliveryDate,
                    startDate: data.startDate,
                    endDate: data.endDate,
                    locked: false,
                    user: { id: this.$store.state.uuid },
                    product: { id: this.$store.state.product.id }
                }
                const response = await http.post("/auctions", requestBody, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                const modalData = {
                    title: "Info (" + response.status + ")",
                    text: "Auction created successfully!"
                }
                window.event.emit("showErrorModal", modalData);
                this.$router.push("/myAuctions")
            } catch(error) {
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
        },
        cancel() {
            this.$router.push("/products")
        }
    }
}
</script>

<style>
</style>