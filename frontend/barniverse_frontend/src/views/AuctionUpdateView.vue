<template>
    <div class="row justify-content-center align-items-center">
        <div class="col-12 col-sm-11 col-md-9 col-lg-9 col-xl-6">

            <AuctionForm :trigger="trigger" :auction="auction" />

            <div class="text-center">
                <button class="btn btn-primary buttonSpacer" @click="triggerValidation">Save Changes</button>
                <button class="btn btn-secondary buttonSpacer" @click="cancel">Cancel</button>
            </div>
        </div>
    </div>
</template>

<script>
import http from "@/http"
import AuctionForm from '@/components/forms/AuctionForm.vue';

export default {
    name: "AuctionUpdateView",
    components: { AuctionForm },
    data: () => ({
        auction: {},
        trigger: false,
    }),
    beforeMount() {
        this.auction = this.$store.state.auction
    },
    mounted() {
        window.event.on("validationCompleted", (data) => {
            this.updateAuction(data)
        });

        // window.event.on("permissionGranted_deleteProduct", () => {
        //     this.deleteProduct()
        // });
    },
    unmounted() {
        window.event.all.delete("validationCompleted");
        // window.event.all.delete("permissionGranted_deleteProduct");
    },
    methods: {
        triggerValidation() {
            this.trigger = !this.trigger
        },
        async updateAuction(data) {
            try {
                const requestBody = {
                    id: data.id,
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
                    user: { id: this.auction.user.id },
                    product: { id: this.auction.product.id }
                }
                const response = await http.put("/auctions", requestBody, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                const modalData = {
                    title: "Info (" + response.status + ")",
                    text: "Product  " + data.title + " updated successfully!"
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
        // getPermission() {
        //     var modalData = {
        //         title: "Warning",
        //         text: "Do you really want to delete " + this.product.title + "?",
        //         id: this.product.id,
        //         type: "deleteProduct"
        //     }
        //     window.event.emit("showPermissionModal", modalData)
        // },
        // async deleteProduct() {
        //     try {
        //         const response = await http.put("/products/deleteWithState/" + this.product.id, null, {
        //             headers: {
        //                 'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
        //             }
        //         })
        //         const modalData = {
        //             title: "Info (" + response.status + ")",
        //             text: "Product " + this.product.title + " deleted successfully!"
        //         }
        //         window.event.emit("showErrorModal", modalData);
        //         this.$router.push("/products");
        //     } catch(error) {
        //         const modalData = {
        //             title: "Error (" + error.response.status + ")",
        //             text: error.response.data
        //         }
        //         window.event.emit("showErrorModal", modalData);
        //     }
        // },
        cancel() {
            this.$router.push("/myAuctions")
        }
    }
}
</script>

<style>
.buttonSpacer {
    margin: 5px;
}
</style>