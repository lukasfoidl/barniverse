import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView"
import ProductView from "../views/ProductView"
import AuctionView from "../views/AuctionView"
import MyAuctionsView from "../views/MyAuctionsView"
import MyOffersView from "../views/MyOffersView"
import RegisterView from "../views/RegisterView"
import LoginView from "../views/LoginView"
import PageNotFoundView from "../views/PageNotFoundView"

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
        component: AuctionView
    },
    {
        name: "myAuctions",
        path: "/myAuctions",
        component: MyAuctionsView
    },
    {
        name: "myOffers",
        path: "/myOffers",
        component: MyOffersView
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