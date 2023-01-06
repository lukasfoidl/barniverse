<template>
    <div class="row justify-content-center align-items-center">
        <div class="col-12 col-sm-10 col-md-8 col-lg-6 col-xl-4">
            <ProductForm :trigger="trigger" :product="product"/>

            <div class="pt-1 mb-4">
                <button class="btn btn-primary" type="button" v-on:click="triggerValidation">Save</button>
            </div>
        </div>
    </div>
</template>

<script>
import http from "@/http"
import ProductForm from '@/components/forms/ProductForm.vue';

export default {
    name: "ProductCreateView",
    components: { ProductForm },
    data: () => ({
        product: {},
        trigger: false,
    }),
    mounted() {
        window.event.on("validationCompleted", (data) => {
            this.createProduct(data)
        });
    },
    unmounted() {
        window.event.all.delete("validationCompleted");
    },
    methods: {
        triggerValidation() {
            this.trigger = !this.trigger
        },
        async createProduct(data) {
            try {
                const response = await http.post("/products", data, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                const modalData = {
                    title: "Info (" + response.status + ")",
                    text: "Product created successfully!"
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
    }
}
</script>

<style>
</style>