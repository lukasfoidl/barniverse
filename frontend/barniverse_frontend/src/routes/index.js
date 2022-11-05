import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView"
import ProductView from "../views/ProductView"
import AuctionView from "../views/AuctionView"
import MyAuctionsView from "../views/MyAuctionsView"
import MyOffersView from "../views/MyOffersView"
import UserView from "../views/UserView"
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
        name: "user",
        path: "/user",
        component: UserView
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