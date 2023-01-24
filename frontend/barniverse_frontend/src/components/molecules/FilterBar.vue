<template>
    <div class="btn-group">
        <button id="filterButton" type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false" data-bs-auto-close="true">
            {{ filter }}
        </button>
        <ul class="dropdown-menu dropdown-menu-end">
            <li><button class="dropdown-item" type="button" @click="onSoon">{{ filters[0] }}</button></li>
            <li><button class="dropdown-item" type="button" @click="onOpen">{{ filters[1] }}</button></li>
            <li><button class="dropdown-item" type="button" @click="onClosed">{{ filters[2] }}</button></li>
            <li v-if="isAdmin">
                <button class="dropdown-item" type="button" @click="onLocked">{{ filters[3] }}</button>
            </li>
        </ul>
    </div>
</template>

<script>
export default {
    name: "FilterBar",
    props: ["filters"],
    data: () => ({
        filter: ""
    }),
    beforeMount() {
        this.filter = this.filters[1];
    },
    computed: {
        isAdmin() {
            return this.$store.getters.isAdmin
        }
    },
    methods: {
        onSoon() {
            this.filter = this.filters[0]
            this.filterData();
        },
        onOpen() {
            this.filter = this.filters[1]
            this.filterData();
        },
        onClosed() {
            this.filter = this.filters[2]
            this.filterData();
        },
        onLocked() {
            this.filter = this.filters[3]
            this.filterData();
        },
        filterData() {
            window.event.emit("filterAuctions", { filter: this.filter })
        }
    }
}
</script>

<style>
#filterButton.btn-check:checked+.btn, #filterButton.btn.active, #filterButton.btn.show, #filterButton.btn:first-child:active, #filterButton:not(.btn-check)+.btn:active {
    border-color: rgba(0, 0, 0, 0);
}
</style>