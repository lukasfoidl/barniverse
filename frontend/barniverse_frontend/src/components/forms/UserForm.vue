<template>
    <div class="row">
        <div class="col-md-6">
            <FirstNameInput :trigger="this.trigger" :firstname="this.user.firstname" :userId="this.user.id" />
        </div>
        <div class="col-md-6">
            <LastNameInput :trigger="this.trigger" :lastname="this.user.lastname" :userId="this.user.id" />
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <EmailInput :trigger="this.trigger" :email="this.user.email" :userId="this.user.id" />
        </div>
        <div class="col-md-6">
            <UsernameInput :trigger="this.trigger" :username="this.user.username" :userId="this.user.id" />
        </div>
    </div>
</template>

<script>
import FirstNameInput from "../molecules/FirstNameInput.vue"
import LastNameInput from "../molecules/LastNameInput.vue"
import EmailInput from "../molecules/EmailInput.vue"
import UsernameInput from "../molecules/UsernameInput.vue"

export default {
    name: "UserForm",
    props: ["trigger", "user"],
    components: { FirstNameInput, LastNameInput, EmailInput, UsernameInput },
    data: () => ({
        values: [
            "firstname",
            "lastname",
            "username",
            "email",
            // profilePicture: ""
        ],
        errors: {},
        validationResults: {}
    }),
    mounted() {
        window.event.on("validationSuccessful", async (data) => {
            if (this.user.id == data.userId) {
                this.checkValidationResults(data);
            }
        })
    },
    unmounted() {
        window.event.all.delete("validationSuccessful");
    },
    methods: {
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
                console.log("UPDATE")
                const modalData = {
                    id: this.user.id,
                    firstname: this.validationResults.firstname,
                    lastname: this.validationResults.lastname,
                    username: this.validationResults.username,
                    email: this.validationResults.email,
                    // picture: this.values.picture,
                    picture: "",
                    state: this.user.state
                }
                window.event.emit("validationCompleted", modalData);                    
            }
        },
    },
    watch: { 
        trigger: function() {
            this.validationResults = {}
        }
    }
}
</script>

<style>
</style>