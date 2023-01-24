<template>
    <div class="row justify-content-center">
        <div class="col-xs-12 col-sm-10 col-md-8 col-lg-6">
            <div class="spaceToText">
                <StatisticCard v-for="statItem in statItems" :key="statItem" :statItem="statItem" />
            </div>
            <div class="justify-content-center d-flex eyecatcherText">
                <center>
                    <a @click="navigateToLogin">
                        Dive into and get the best deals in the whole Barniverse!
                    </a>
                </center>
            </div>
        </div>
    </div>
</template>

<script>
import http from "@/http"
import StatisticCard from "@/components/cards/StatisticCard.vue";

export default {
    name: "HomeView",
    data: () => ({
        statItems: {}
    }),
    components: { StatisticCard },
    methods: {
        async requestStatistics() {
            try {
                const response = await http.get("statistics")
                this.statItems = response.data
            } catch (error) {
                console.log(error)
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
        },
        navigateToLogin() {
            this.$router.push("/login")
        }
    },
    beforeMount() {
        this.requestStatistics();
    },
}
</script>

<style>
.spaceToText {
    padding-bottom: 30px;
}

.eyecatcherText {
    color: #4b3535;
    font-size: 20px;
    text-decoration: underline;
    cursor: pointer;
}
</style>