<template>
    <div class="row justify-content-center align-items-center">
        <div class="col-12 col-sm-11 col-md-9 col-lg-9 col-xl-6">

            <OfferForm :trigger="trigger" :auction="auction" />

            <div class="text-center">
                <button class="btn btn-primary buttonSpacer" @click="triggerValidation">Save</button>
                <button class="btn btn-secondary buttonSpacer" @click="cancel">Cancel</button>
            </div>
        </div>
    </div>
</template>

<script>
import http from "@/http"
import OfferForm from '@/components/forms/OfferForm.vue';

export default {
    name: "OfferCreateView",
    components: { OfferForm },
    data: () => ({
        auction: {},
        trigger: false,
    }),
    beforeMount() {
        this.auction = this.$store.state.auction
    },
    mounted() {
        window.event.on("validationCompleted", (data) => {
            this.createOffer(data)
        });
    },
    unmounted() {
        window.event.all.delete("validationCompleted");
    },
    methods: {
        triggerValidation() {
            this.trigger = !this.trigger
        },
        async createOffer(data) {
            try {
                const requestBody = {
                    price: data.price,
                    quantity: data.quantity,
                    deliveryDate: data.deliveryDate,
                    user: { id: this.$store.state.uuid },
                    auction: { id: this.auction.id }
                }
                const response = await http.post("/offers", requestBody, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                const modalData = {
                    title: "Info (" + response.status + ")",
                    text: "Offer created successfully!"
                }
                window.event.emit("showErrorModal", modalData);
                this.$router.push("/myOffers")
            } catch(error) {
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
        },
        cancel() {
            this.$router.push("/auctions")
        }
    }
}
</script>

<style>
</style>