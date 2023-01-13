<template>

    <div class="form-outline">
        <!-- Confirm Password-->
        <label class="form-label" for="confirmationPassword">Confirm Password</label>
        <input type="password" :id="'confirmationPassword' + objectId" class="form-control" v-model="value" @blur="validate(false)" />
        <div class="" :id="'feedback-confirmationPassword' + objectId">
            <p :id="'error-confirmationPassword' + objectId" class="errorMessage">&nbsp;</p>
        </div>
    </div>

</template>

<script>
import { object, string } from "yup"

export default {
    name: "ConfirmationPasswordInput",
    props: ["trigger", "objectId"],
    data: () => ({
        value: "",
        password : ""
    }),
    mounted() {
        window.event.on("passwordInputChanged", (data) => {
            this.password = data
        });
    },
    unmounted() {
        window.event.all.delete("passwordInputChanged");
    },
    methods: {
        validate(shouldSendEvent) {
            var values = { confirmationPassword: this.value, password: this.password }; // necessary for successful validation (field/value object)
            const data = {
                field: "confirmationPassword",
                values: values,
                objectId: this.objectId,
                shouldSendEvent: shouldSendEvent,
                validationSchema: validationSchema
            }
            window.event.emit("validateInput", data)
        }
    },
    watch: { 
        trigger: function() {
            this.validate(true)
        }
    }
}

const validationSchema = object().shape({
    confirmationPassword: string().min(8, "Password must be at least 8 Characters!").required("Confirm Password is required!").test('passwords-match', 'Passwords must match', function () {
        return this.parent.password === this.parent.confirmationPassword
    }),
})
</script>

<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>