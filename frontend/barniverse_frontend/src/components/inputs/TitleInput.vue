<template>

    <div class="form-outline">
        <!-- Title -->
        <label class="form-label" for="title">Title</label>
        <input type="text" :id="'title' + objectId" class="form-control" v-model="value" aria-describedby="title" @blur="validate(false)" />
        <div class="" :id="'feedback-title' + objectId">
            <p :id="'error-title' + objectId" class="errorMessage">&nbsp;</p>
        </div>
    </div>

</template>
    
<script>
import { object, string } from "yup"

export default {
    name: "TitleInput",
    props: ["trigger", "title", "objectId"],
    data: () => ({
        value: "",
    }),
    mounted() {
        this.value = this.title
    },
    methods: {
        validate(shouldSendEvent) {
            var values = { title: this.value }; // necessary for successful validation (field/value object)
            const data = {
                field: "title",
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
    title: string().required("Title is required!"),
})
</script>
    
<style>
.errorMessage {
    font-size: 11px;
    margin-bottom: 0%;
}
</style>