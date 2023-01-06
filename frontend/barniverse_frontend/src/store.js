// import vuex store
import { createStore } from 'vuex'

const store = createStore({
    state() {
        return {
            roles: {
                ROLE_OBSERVER: "ROLE_OBSERVER",
                ROLE_USER: "ROLE_USER",
                ROLE_ADMIN: "ROLE_ADMIN"
            },
            role: "ROLE_OBSERVER",
            username: "",
            uuid: "",
            product: {},
        }
    },
    mutations: {
        // role management
        setRole(state, data) {
            if (data.role == state.roles.ROLE_ADMIN) {
                return state.role = state.roles.ROLE_ADMIN
            }
            if (data.role == state.roles.ROLE_USER) {
                return state.role = state.roles.ROLE_USER
            }   
            return state.role = state.roles.ROLE_OBSERVER
        },
        setUsername(state, data) {
            return state.username = data.username
        },
        setUUID(state, data) {
            return state.uuid = data.uuid
        },

        // product store for ProductUpdateView
        saveProduct(state, data) {
            state.product = data.product
        },
    },
    getters: {
        isAdmin(state) {
            return state.role == state.roles.ROLE_ADMIN
        },
        isUser(state) {
            return state.role == state.roles.ROLE_USER
        },
        isObserver(state) {
            return state.role == state.roles.ROLE_OBSERVER
        },
    }
})

export default store