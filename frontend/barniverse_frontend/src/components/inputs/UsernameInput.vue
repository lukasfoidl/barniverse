<template>

    <div class="form-outline ">
        <!-- Username-->
        <label class="form-label" for="Username">Username</label>
        <input class="form-control" type="text" v-model="value" :id="'username' + objectId" @blur="validate(false)" />
        <div class="" :id="'feedback-username' + objectId">
            <p :id="'error-username' + objectId" class="errorMessage">&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "UsernameInput",
    props: ["trigger", "username", "objectId"],
    data: () => ({
        value: "",
    }),
    mounted() {
        this.value = this.username
    },
    methods: {
        validate(shouldSendEvent) {
            var values = { username: this.value }; // necessary for successful validation (field/value object)
            const data = {
                field: "username",
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
    username: string().min(5, "Username must be between 5 and 25 Characters long!").max(25, "Username must be between 5 and 25 Characters long!").required("Username is required!"),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>