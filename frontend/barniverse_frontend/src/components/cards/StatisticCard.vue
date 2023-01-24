<template>
    <div class="card bigCard">
        <div class="justify-content-center d-flex numberFormat">
            {{ statItem.value }}
        </div>
        <div class="justify-content-center d-flex">
            {{ statItem.explenation }}
        </div>
    </div>
</template>

<script>
export default {
    name: "StatisticCard",
    props: ["statItem"],
}
</script>

<style>
.card {
    margin: 10px;
    padding: 10px;
}

.bigCard {
    align-content: center;
    min-height: 200px;
    display: grid;
}

.card-body {
    padding-bottom: 0px;
}

.numberFormat {
    font-size: 60px;
    font-weight: bold;
    font-family: monospace;
    color: #4b3535;
}
</style>