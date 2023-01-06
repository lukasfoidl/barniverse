<template>

    <div class="form-outline">
        <!-- Password-->
        <label class="form-label" for="password">Password</label>
        <input type="password" :id="'password' + userId" class="form-control" v-model="value" @change="passwordInputChanged"
            @blur="validate('password', false)" />
        <div class="" :id="'feedback-password' + userId">
            <p class="errorMessage">{{ error }}&nbsp;</p>
        </div>
    </div>

</template>

<script>
import { object, string } from "yup"

export default {
    name: "PasswordInput",
    props: ["trigger", "userId"],
    data: () => ({
        value: "",
        error: "",
    }),
    methods: {
        validate(field, shouldSendEvent) {
            var values = { password: this.value }; // necessary for successful validation (field/value object)
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
        },
        passwordInputChanged() {
            window.event.emit("passwordInputChanged", this.value);
        }
    },
    watch: { 
        trigger: function() {
            this.validate("password", true)
        }
    }
}

const validationSchema = object().shape({
    password: string().min(8, "Password must be at least 8 Characters!").required("Password is required!"),
})
</script>

<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>