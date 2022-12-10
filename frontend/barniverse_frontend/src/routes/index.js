import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView"
import ProductView from "../views/ProductView"
import AuctionView from "../views/AuctionView"
import MyAuctionsView from "../views/MyAuctionsView"
import MyOffersView from "../views/MyOffersView"
import RegisterView from "../views/RegisterView"
import LoginView from "../views/LoginView"
import HelpView from "../views/HelpView"
import ImprintView from "../views/ImprintView"
import UserView from "../views/UserView"
import PageNotFoundView from "../views/PageNotFoundView"

//WARNING: routes are not locked according the user role yet

// import jwtDecoder from 'vue-jwt-decode'

// var role = "";
// const roles = {
//     ROLE_USER: "ROLE_USER",
//     ROLE_ADMIN: "ROLE_ADMIN"
// }

// window.event.on('reloadJWT', () => {
//     reloadJWT();
// });

// function reloadJWT() {
//     const jwt = jwtDecoder.decode(sessionStorage.getItem("jwt-token") ?? "")
//     role =  jwt == null ? "" : jwt.role
// }

const routes = [
    {
        name: "home",
        path: "/",
        component: HomeView,
    },
    {
        name: "products",
        path: "/products",
        component: ProductView
    },
    {
        name: "auctions",
        path: "/auctions",
        component: AuctionView,
    },
    {
        name: "myAuctions",
        path: "/myAuctions",
        component: MyAuctionsView,
        // beforeEnter: () => {
        //     if (role != roles.ROLE_USER || role != roles.ROLE_ADMIN) {
        //         return '/'
        //     }
        // },
    },
    {
        name: "myOffers",
        path: "/myOffers",
        component: MyOffersView,
        // beforeEnter: () => {
        //     if (role != roles.ROLE_USER || role != roles.ROLE_ADMIN) {
        //         return '/'
        //     }
        // },
    },
    {
        name: "register",
        path: "/register",
        component: RegisterView
    },
    {
        name: "login",
        path: "/login",
        component: LoginView
    },
    {
        name: "help",
        path: "/help",
        component: HelpView
    },
    {
        name: "imprint",
        path: "/imprint",
        component: ImprintView
    },
    {
        name: "user",
        path: "/user",
        component: UserView,
        // beforeEnter: () => {
        //     if (role != roles.ROLE_ADMIN) {
        //         return '/'
        //     }
        // },
    },
    {
        path: '/:catchAll(.*)*',
        name: "PageNotFoundView",
        component: PageNotFoundView
    },
];

const router = createRouter({
    routes,
    history: createWebHistory(process.env.BASE_URL),
})

export default router;