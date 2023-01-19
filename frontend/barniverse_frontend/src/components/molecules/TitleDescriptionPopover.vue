<template>
    <Popover placement="right" :hover="true">
        <i class="bi bi-info-circle icon" alt="Details"></i>
        <template #content>
            <div class="popover">
                <h5>{{ object.title }}</h5>
                <div>{{ object.description }}</div>
            </div>
        </template>
    </Popover>
</template>

<script>
import Popover from "vue3-popper"

export default {
    name: "TitleDescriptionPopover",
    props: ["object"],
    components: { Popover },
}
</script>

<style>
.icon {
    color: black;
}

.popover {
    --popper-theme-background-color: #ffffff;
    --popper-theme-background-color-hover: #ffffff;
    --popper-theme-text-color: #333333;
    --popper-theme-border-width: 1px;
    --popper-theme-border-style: solid;
    --popper-theme-border-color: #eeeeee;
    --popper-theme-border-radius: 6px;
    --popper-theme-padding: 32px;
    --popper-theme-box-shadow: 0 6px 30px -6px rgba(0, 0, 0, 0.25);
    padding: 1rem;
}
</style>