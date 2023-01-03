import { createApp } from 'vue'
import App from '@/App.vue'

import jquery from "jquery"
window.$ = jquery

// import "bootstrap"
import "bootstrap/dist/css/bootstrap.min.css"
import 'bootstrap-icons/font/bootstrap-icons.css'

import bootstrap from "bootstrap/dist/js/bootstrap.min.js"
window.bootstrap = bootstrap

import mitt from 'mitt';
window.event = mitt();

import router from "@/routes";
window.router = router

window.roles = {
    ROLE_USER: "ROLE_USER",
    ROLE_ADMIN: "ROLE_ADMIN"
}
window.role = "ROLE_OBSERVER"

const app = createApp(App)

app.use(router);

app.mount('#app')