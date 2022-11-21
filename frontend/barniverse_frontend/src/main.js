import { createApp } from 'vue'
import App from './App.vue'

import jquery from "jquery"
window.$ = jquery

// import "bootstrap"
import "bootstrap/dist/css/bootstrap.min.css"
import 'bootstrap-icons/font/bootstrap-icons.css'

import bootstrap from "bootstrap/dist/js/bootstrap.min.js"
window.bootstrap = bootstrap

import router from "./routes";

const app = createApp(App)

app.use(router);

app.mount('#app')