<template>
    <div class="row justify-content-center align-items-center">
        <div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-4">
            <ProductForm :trigger="trigger" :product="product"/>

            <div class="text-center">
                <button class="btn btn-primary buttonSpacer" type="button" v-on:click="triggerValidation">Save Changes</button>
                <button class="btn btn-danger buttonSpacer" type="button" v-on:click="getPermission">Delete</button>
                <button class="btn btn-secondary buttonSpacer" type="button" v-on:click="cancel">Cancel</button>
            </div>
        </div>
    </div>
</template>

<script>
import http from "@/http"
import ProductForm from '@/components/forms/ProductForm.vue';

export default {
    name: "ProductUpdateView",
    components: { ProductForm },
    data: () => ({
        product: {},
        trigger: false,
    }),
    beforeMount() {
        this.product = this.$store.state.product
    },
    mounted() {
        window.event.on("validationCompleted", (data) => {
            this.updateProduct(data)
        });

        window.event.on("permissionGranted_deleteProduct", () => {
            this.deleteProduct()
        });
    },
    unmounted() {
        window.event.all.delete("validationCompleted");
        window.event.all.delete("permissionGranted_deleteProduct");
    },
    methods: {
        triggerValidation() {
            this.trigger = !this.trigger
        },
        async updateProduct(data) {
            try {
                const requestBody = {
                    id: data.id,
                    title: data.title,
                    description: data.description,
                    images: this.product.images // because images are not fully implmented yet
                }
                const response = await http.put("/products", requestBody, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                const modalData = {
                    title: "Info (" + response.status + ")",
                    text: "Product  " + data.title + " updated successfully!"
                }
                window.event.emit("showErrorModal", modalData);
                this.$router.push("/products")
            } catch(error) {
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
        },
        getPermission() {
            var modalData = {
                title: "Warning",
                text: "Do you really want to delete " + this.product.title + "?",
                id: this.product.id,
                type: "deleteProduct"
            }
            window.event.emit("showPermissionModal", modalData)
        },
        async deleteProduct() {
            try {
                const response = await http.put("/products/" + this.product.id + "/deleteWithState", null, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                const modalData = {
                    title: "Info (" + response.status + ")",
                    text: "Product " + this.product.title + " deleted successfully!"
                }
                window.event.emit("showErrorModal", modalData);
                this.$router.push("/products");
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
.buttonSpacer {
    margin: 5px;
}
</style>