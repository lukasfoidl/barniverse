<template>
    <div class="modal fade" id="permissionModal" tabindex="-1" role="dialog" aria-labelledby="permissionModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="permissionModalTitle"></h5>
                    <button type="button" class="close cancel" data-bs-dismiss="modal" aria-label="Close">
                        <i class="bi bi-x"></i>
                    </button>
                </div>
                <div class="modal-body">
                    <div id="permissionModalContent"></div>
                </div>
                <div class="modal-footer">
                    <button :id="this.id" type="button" class="btn btn-warning" data-bs-dismiss="modal" @click="permissionGranted">Continue</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "PermissionModal",
    data: () => ({
        id: "",
        type: ""
    }),
    mounted() {
        window.event.on("showPermissionModal", (data) => {
            window.$("#permissionModalTitle").text(data.title);
            window.$("#permissionModalContent").text(data.text);
            this.id = data.id;
            this.type = data.type;
            new window.bootstrap.Modal(window.$("#permissionModal"), {}).show();
        });
    },
    methods: {
        permissionGranted() {
            window.event.emit("permissionGranted_" + this.type, this.id)
        }
    }
}

// new window.bootstrap.Modal(window.$("#errorModal"), {}).show(); --> show Modal through javascript because >> window.$('#errorModal').modal('show') << is not working
</script>

<style>
.cancel {
    border: none;
    background: none;
}

.cancel i {
    font-size: 1.5em;
}
</style>