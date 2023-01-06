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

// import router
import router from "@/routes";
window.router = router

// import vuex store
import { createStore } from 'vuex'
const store = createStore({
    state() {
        return {
            product: {}
        }
    },
    mutations: {
        saveProduct(state, product) {
            state.product = product.product
        }
    }
})

window.roles = {
    ROLE_USER: "ROLE_USER",
    ROLE_ADMIN: "ROLE_ADMIN"
}
window.role = "ROLE_OBSERVER"

const app = createApp(App)

app.use(router);
app.use(store)

app.mount('#app')