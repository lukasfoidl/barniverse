<template>
    <div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="changePasswordModalLabel" @click.self="clearInputs"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="changePasswordModalTitle">Change Password</h5>
                    <button type="button" class="close cancel" data-bs-dismiss="modal" aria-label="Close" @click="clearInputs">
                        <i class="bi bi-x"></i>
                    </button>
                </div>
                <div class="modal-body">
                    <PasswordInput :key="key" :trigger="trigger"/>
                    <ConfirmationPasswordInput :key="key" :trigger="trigger" />                        
                </div>
                <div class="modal-footer">
                    <button :id="this.id" type="button" class="btn btn-primary" @click="getValues">Change Password</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" @click="clearInputs">Cancel</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import http from "@/http"
import ConfirmationPasswordInput from "./ConfirmationPasswordInput.vue";
import PasswordInput from "./PasswordInput.vue";

export default {
    name: "ChangePasswordModal",
    props: ["id"],
    data: () => ({
        values: [
            "password",
            "confirmationPassword"
        ],
        validationResults: {},
        trigger: false,
        modal: null,
        key: false
    }),
    mounted() {
        this.modal = new window.bootstrap.Modal(window.$("#changePasswordModal"), {})
       
        window.event.on("showChangePasswordModal", () => {
            this.modal.show();
        });
        window.event.on("validationSuccessful", async (data) => {
            this.checkValidationResults(data);
        })
    },
    unmounted() {
        window.event.all.delete("showChangePasswordModal");
        window.event.all.delete("validationSuccessful");
    },
    components: { PasswordInput, ConfirmationPasswordInput },
    methods: {
        getValues() {
            this.validationResults = {}
            this.trigger = !this.trigger // change of trigger triggers validation events of children
        },
        async checkValidationResults(data) {
            // save validation results
            this.validationResults[data.field] = data.value;

            // only if all results received
            if (Object.keys(this.validationResults).length === this.values.length) {
                // check if all values have been successfully validated and added to validationResults
                for (var index in this.values) {
                    var foundKey = false
                    for (var key in this.validationResults) {
                        if (this.values[index] === key) {
                            foundKey = true;
                            break;
                        }
                    }
                    if (!foundKey) {
                        return;
                    }
                }
                try {
                    console.log("CHANGE PASSWORD")
                    await this.changePassword();
                } catch (error) {
                    const modalData = {
                        title: "Error (" + error.response.status + ")",
                        text: error.response.data
                    }
                    window.event.emit("showErrorModal", modalData);
                }
            }
        },
        async changePassword() {
            try {
                const data = {
                    id: this.id,
                    password: this.validationResults.password,
                }
                const response = await http.put("users/changePassword", data, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                this.modal.hide();
                const modalData = {
                    title: "Info (" + response.status + ")",
                    text: "Password updated successfully!"
                }
                window.event.emit("showErrorModal", modalData);
            } catch (error) {
                this.modal.hide();
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
            this.clearInputs();
        },
        clearInputs() {
            this.key = !this.key // rerender child components (clear inputs)
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