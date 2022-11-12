<template>
    <div>
        HomeView
    </div>
    <div>
        <button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#errorModal">
            Launch demo modal
        </button>

        <!-- <button v-on:click="OnSimulateError" class="btn btn-secondary">Simulate Error</button> -->
    </div>
</template>

<script>
// import $ from "jquery"

export default {
    name: "HomeView",
    methods: {
        OnSimulateError() {
            // $("#errorModalContent").innerHTML = "An Error occured!";
            // $.noConflict()
            // $("#errorModal").modal("show");
        }
    }
}
</script>

<style>

</style>