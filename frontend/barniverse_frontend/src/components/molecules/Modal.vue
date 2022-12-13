<template>
    <div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="errorModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="errorModalTitle"></h5>
                    <button type="button" class="close cancel" data-bs-dismiss="modal" aria-label="Close">
                        <i class="bi bi-x"></i>
                    </button>
                </div>
                <div class="modal-body">
                    <div id="errorModalContent"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "Modal",
    mounted() {
        window.event.on("showModal", (data) => {
            window.$("#errorModalTitle").text(data.title);
            window.$("#errorModalContent").text(data.text);
            new window.bootstrap.Modal(window.$("#errorModal"), {}).show();
        });
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