<template>
    <div>
        HomeView
    </div>
    <div>
        <button v-on:click="OnSimulateError" class="btn btn-secondary">Simulate Error</button>
    </div>
</template>

<script>
export default {
    name: "HomeView",
    methods: {
        OnSimulateError() {
            window.$("#errorModalTitle").text("Demo-Error");
            window.$("#errorModalContent").text("A Demo-Error occured!");
            new window.bootstrap.Modal(window.$("#errorModal"), {}).show();
            // window.$("#errorModal").modal("show")
        },
    }
}
</script>

<style>

</style>