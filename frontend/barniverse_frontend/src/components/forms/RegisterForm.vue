<template>
    <div class="row">
        <div class="col-md-6">
            <FirstNameInput :trigger="trigger"/>
        </div>
        <div class="col-md-6">
            <LastNameInput :trigger="trigger" />
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <EmailInput :trigger="trigger"/>
        </div>
        <div class="col-md-6">
            <UsernameInput :trigger="trigger" />
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <PasswordInput :trigger="trigger" />
        </div>
        <div class="col-md-6">
            <ConfirmationPasswordInput :trigger="trigger" />
        </div>
    </div>
    <!-- <div class="row">
        <ProfilePictureInput :trigger="trigger" />
    </div> -->

    <!-- <div class="row">
        <div class="col-md-12">
            <div class="form-outline">
                <label class="form-label" for="emailAddress">Profile Picture</label>
                <input type="file" class="form-control" v-on:change="values.profilePicture"
                    placeholder="Photo" capture @blur="validate('profilePicture')" />
                <div class="" id="feedback-confirmationPassword">
                    <p class="errorMessage">{{ errors.profilePicture }}&nbsp;</p>
                </div>
            </div>
        </div>
    </div> -->

    <InputValidationHandler :trigger="trigger" :objectId="objectId" :values="values" />
</template>

<script>
import FirstNameInput from "@/components/inputs/FirstNameInput.vue"
import LastNameInput from "@/components/inputs/LastNameInput.vue"
import EmailInput from "@/components/inputs/EmailInput.vue"
import UsernameInput from "@/components/inputs/UsernameInput.vue"
import ConfirmationPasswordInput from "@/components/inputs/ConfirmationPasswordInput.vue"
import PasswordInput from "@/components/inputs/PasswordInput.vue"
import InputValidationHandler from "../inputs/InputValidationHandler.vue"

export default {
    name: "RegisterForm",
    props: ["trigger"],
    data: () => ({
        values: [
            "firstname",
            "lastname",
            "username",
            "email",
            "password",
            "confirmationPassword",
        ],
        objectId: undefined
    }),
    components: { FirstNameInput, LastNameInput, EmailInput, UsernameInput, ConfirmationPasswordInput, PasswordInput, InputValidationHandler },
}
</script>

<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}

.invalid-feedback,
.valid-feedback {
    margin-top: 0 !important;
}
</style>