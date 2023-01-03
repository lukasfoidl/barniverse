<template>

    <div class="form-outline ">
        <!-- Username-->
        <label class="form-label" for="Username">Username</label>
        <input class="form-control" type="text" v-model="values.username" :id="'username' + userId"
            @blur="validate('username', false)" />
        <div class="" :id="'feedback-username' + userId">
            <p class="errorMessage">{{ errors.username }}&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "UsernameInput",
    props: ["trigger", "username", "userId"],
    data: () => ({
        values: {
            username: "",
        },
        errors: {
            username: "",
        },
    }),
    mounted() {
        this.values.username = this.username
    },
    methods: {
        validate(field, shouldSendEvent) {
            registerFormSchema
                .validateAt(field, this.values)
                .then(() => {
                    this.errors[field] = ""
                    window.$("#" + field + this.userId).removeClass("is-invalid");
                    window.$("#" + field + this.userId).addClass("is-valid");
                    window.$("#feedback-" + field + this.userId).removeClass("invalid-feedback");
                    window.$("#feedback-" + field + this.userId).addClass("valid-feedback");
                    this.sendValidationResults(field, shouldSendEvent)
                })
                .catch((error) => {
                    this.errors[field] = error.message
                    window.$("#" + field + this.userId).removeClass("is-valid");
                    window.$("#" + field + this.userId).addClass("is-invalid");
                    window.$("#feedback-" + field + this.userId).removeClass("valid-feedback");
                    window.$("#feedback-" + field + this.userId).addClass("invalid-feedback");
                })
        },
        sendValidationResults(field, shouldSendEvent) {
            if (shouldSendEvent) { // only send event if validation was triggered by trigger/button and not single validation
                const modalData = {
                    field: field,
                    value: this.values.username,
                    userId: this.userId
                }
                window.event.emit("validationSuccessful", modalData);
            }
        }
    },
    watch: { 
        trigger: function() {
            this.validate("username", true)
        }
    }
}

//validate here
const registerFormSchema = object().shape({
    username: string().min(5, "Username must be between 5 and 20 Characters long!").max(20, "Username must be between 5 and 20 Characters long!").required("Username is required!"),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>