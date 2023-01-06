<template>

    <div class="form-outline ">
        <!-- Username-->
        <label class="form-label" for="Username">Username</label>
        <input class="form-control" type="text" v-model="value" :id="'username' + userId"
            @blur="validate('username', false)" />
        <div class="" :id="'feedback-username' + userId">
            <p class="errorMessage">{{ error }}&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "UsernameInput",
    props: ["trigger", "username", "userId"],
    data: () => ({
        value: "",
        error: "",
    }),
    mounted() {
        this.value = this.username
    },
    methods: {
        validate(field, shouldSendEvent) {
            var values = { username: this.value }; // necessary for successful validation (field/value object)
            validationSchema
                .validateAt(field, values)
                .then(() => {
                    this.error = ""
                    const data = {
                        field: field,
                        value: this.value,
                        objectId: this.userId,
                        shouldSendEvent: shouldSendEvent
                    }
                    window.event.emit("updateValidationSuccess", data)
                })
                .catch((error) => {
                    this.error = error.message
                    const data = {
                        field: field,
                        objectId: this.userId
                    }
                    window.event.emit("updateValidationError", data)
                })
        }
    },
    watch: { 
        trigger: function() {
            this.validate("username", true)
        }
    }
}

const validationSchema = object().shape({
    username: string().min(5, "Username must be between 5 and 20 Characters long!").max(20, "Username must be between 5 and 20 Characters long!").required("Username is required!"),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>