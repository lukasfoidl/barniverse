<template>

    <div class="form-outline">
        <!-- Password-->
        <label class="form-label" for="password">Password</label>
        <input type="password" :id="'password' + objectId" class="form-control" v-model="value" @change="passwordInputChanged" @blur="validate(false)" />
        <div class="" :id="'feedback-password' + objectId">
            <p :id="'error-password' + objectId" class="errorMessage">&nbsp;</p>
        </div>
    </div>

</template>

<script>
import { object, string } from "yup"

export default {
    name: "PasswordInput",
    props: ["trigger", "objectId"],
    data: () => ({
        value: "",
    }),
    methods: {
        validate(shouldSendEvent) {
            var values = { password: this.value }; // necessary for successful validation (field/value object)
            const data = {
                field: "password",
                values: values,
                objectId: this.objectId,
                shouldSendEvent: shouldSendEvent,
                validationSchema: validationSchema
            }
            window.event.emit("validateInput", data)
        },
        passwordInputChanged() {
            window.event.emit("passwordInputChanged", this.value);
        }
    },
    watch: { 
        trigger: function() {
            this.validate(true)
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