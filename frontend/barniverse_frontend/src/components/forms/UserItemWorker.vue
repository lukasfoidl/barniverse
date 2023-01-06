<template>
    <div class="accordion-item">
        <h2 class="accordion-header" :id="'heading' + this.userData.id">
            <button :class="{deletedStyle: isUserDeleted(), blockedStyle: isUserBlocked()}" class="accordion-button collapsed accordionStyle" type="button" data-bs-toggle="collapse"
                :data-bs-target="'#user' + this.userData.id" aria-expanded="false" :aria-controls="'#user' + this.userData.id">
                <div class="col-md-6 col-lg-6 wordWrap">
                    <span>{{ userData.firstname }}</span>
                    <span>&nbsp;</span>
                    <span>{{ userData.lastname }}</span>
                    <span> | </span>
                    <span>{{ userData.username }}</span>
                </div>
                <div class="wordWrap col-md-5 col-lg-5 invisible-xs">
                    {{ userData.email }}
                </div>
            </button>
        </h2>
        <div :id="'user' + this.userData.id" class="accordion-collapse collapse" :aria-labelledby="'#heading' + this.userData.id"
            data-bs-parent="#userList">
            <div class="accordion-body">
                <UserForm :key="userData.id" :trigger="this.trigger" :user="userData" />
                
                <div class="text-center">
                    <button :class="{disabled: isUserDeleted()}" class="btn btn-primary buttonSpacer" @click="triggerValidation">
                        Save Changes
                    </button>
                    <button :class="{disabled: isUserDeleted()}" class="btn btn-info buttonSpacer" @click="toggleAdmin">
                        {{ getAdminText() }}
                    </button>
                    <button :class="{disabled: isUserDeleted()}" class="btn btn-warning buttonSpacer" @click="toggleState">
                        {{ getStateText() }}
                    </button>
                    <button :class="{disabled: isUserDeleted()}" class="btn btn-danger buttonSpacer" @click="getPermission">
                        Delete
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import http from "@/http"
import UserForm from '@/components/forms/UserForm.vue';

export default {
    name: "UserItemWorker",
    props: ["user"],
    components: { UserForm },
    data: () => ({
        trigger: false,
        userData: {},
    }),
    beforeMount() {
        this.userData = this.user;
    },
    mounted() {
        window.event.on("permissionGranted_deleteUser", (id) => {
            if (id == this.userData.id) {
                this.deleteUser()
            }
        });
        window.event.on("validationCompleted", (data) => {
            if (this.user.id == data.id) {
                this.updateUser(data)
            }
        });
    },
    unmounted() {
        window.event.all.delete("permissionGranted_deleteUser");
        window.event.all.delete("validationCompleted");
    },
    methods: {
        // Save Changes Button
        triggerValidation() {
            this.trigger = !this.trigger // change of trigger triggers validation events of children
        },
        // Delete Profile Button
        getPermission() {
            var modalData = {
                title: "Warning",
                text: "Do you really want to delete " + this.userData.username + "?",
                id: this.userData.id,
                type: "deleteUser"
            }
            window.event.emit("showPermissionModal", modalData)
        },
        async deleteUser() {
            try {
                const response = await http.put("users/deleteWithState/" + this.userData.id, null, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                this.userData.state = "deleted";
                const modalData = {
                    title: "Info (" + response.status + ")",
                    text: "User " + this.userData.username + " deleted successfully!"
                }
                window.event.emit("showErrorModal", modalData);
            } catch(error) {
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
        },
        async updateUser(data) {
            try {
                const response = await http.put("users", data, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                this.userData = data;
                const modalData = {
                    title: "Info (" + response.status + ")",
                    text: "User " + this.userData.username + " updated successfully!"
                }
                window.event.emit("showErrorModal", modalData);
            } catch(error) {
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
        },
        async toggleState() {
            try {
                const response = await http.put("users/toggleState/" + this.userData.id, null, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                this.userData.state = response.data;
                const modalData = {
                    title: "Info (" + response.status + ")",
                    text: "User state of " + this.userData.username + " updated successfully!"
                }
                window.event.emit("showErrorModal", modalData);
            } catch (error) {
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
        },
        async toggleAdmin() {
            try {
                const response = await http.put("users/toggleAdmin/" + this.userData.id, null, {
                    headers: {
                        'Authorization': `Bearer ${sessionStorage.getItem("jwt-token")}`
                    }
                })
                this.userData.isAdmin = !this.userData.isAdmin
                const modalData = {
                    title: "Info (" + response.status + ")",
                    text: "Admin state of " + this.userData.username + " updated successfully!"
                }
                window.event.emit("showErrorModal", modalData);
            } catch (error) {
                const modalData = {
                    title: "Error (" + error.response.status + ")",
                    text: error.response.data
                }
                window.event.emit("showErrorModal", modalData);
            }
        },
        getAdminText() {
            return this.userData.isAdmin ? "Remove admin" : "Add admin"
        },
        getStateText() {
            if (this.userData.state == "active") {
                return "Deactivate"
            } else if (this.userData.state == "blocked") {
                return "Activate"
            } else if (this.userData.state == "deleted") {
                return "Activate"
            }
        },
        isUserDeleted() {
            return this.userData.state == "deleted"
        },
        isUserBlocked() {
            return this.userData.state == "blocked"
        }
    }
}
</script>

<style>
.wordWrap {
    word-break: break-word;
}

.buttonSpacer {
   margin: 5px;
}

.deletedStyle, .deletedStyle:hover, .deletedStyle:focus, .deletedStyle:not(.collapsed) {
    color: #ff00007a
}

.blockedStyle, .blockedStyle:hover, .blockedStyle:focus, .blockedStyle:not(.collapsed) {
    color: #8080807a    
}

@media(max-width: 767px) {
    .invisible-xs {
        display: none;
    }
}
</style>