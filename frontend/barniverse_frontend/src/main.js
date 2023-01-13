import { createApp } from 'vue'
import App from '@/App.vue'

// import jQuery
import jquery from "jquery"
window.$ = jquery

// import "bootstrap"
import "bootstrap/dist/css/bootstrap.min.css"
import 'bootstrap-icons/font/bootstrap-icons.css'

import bootstrap from "bootstrap/dist/js/bootstrap.min.js"
window.bootstrap = bootstrap

// import mitt for events
import mitt from 'mitt';
window.event = mitt();

// import store
import store from "@/store"

// import router
import router from "@/routes";

//---------- CREATE APP ----------

const app = createApp(App)

app.use(store)
app.use(router);

app.mount('#app')