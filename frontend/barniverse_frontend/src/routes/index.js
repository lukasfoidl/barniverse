import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView"
import ProductView from "@/views/ProductView"
import AuctionView from "@/views/AuctionView"
import MyAuctionsView from "@/views/MyAuctionsView"
import MyOffersView from "@/views/MyOffersView"
import RegisterView from "@/views/RegisterView"
import LoginView from "@/views/LoginView"
import HelpView from "@/views/HelpView"
import ImprintView from "@/views/ImprintView"
import UserView from "@/views/UserView"
import PageNotFoundView from "@/views/PageNotFoundView"
import ProfileView from "@/views/ProfileView"
import ProductUpdateView from "@/views/ProductUpdateView"
import ProductCreateView from "@/views/ProductCreateView"

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
        beforeEnter: () => {
            if (window.role != window.roles.ROLE_USER && window.role != window.roles.ROLE_ADMIN) {
                return '/PageNotFound'
            }
        }
    },
    {
        name: "myOffers",
        path: "/myOffers",
        component: MyOffersView,
        beforeEnter: () => {
            if (window.role != window.roles.ROLE_USER && window.role != window.roles.ROLE_ADMIN) {
                return '/PageNotFound'
            }
        }
    },
    {
        name: 'user',
        path: '/user', 
        component: UserView,
        beforeEnter: () => {
            if (window.role != window.roles.ROLE_ADMIN) {
                return '/PageNotFound'
            }
        }
    },
    {
        name: "profile",
        path: "/profile",
        component: ProfileView,
        beforeEnter: () => {
            if (window.role != window.roles.ROLE_USER && window.role != window.roles.ROLE_ADMIN) {
                return '/PageNotFound'
            }
        }
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
        path: '/:catchAll(.*)*',
        name: "PageNotFoundView",
        component: PageNotFoundView
    },
    {
        name: "updateProduct",
        path: "/products/update/",
        component: ProductUpdateView,
        beforeEnter: () => {
            if (window.role != window.roles.ROLE_ADMIN) {
                return '/PageNotFound'
            }
        }
    },
    {
        name: "createProduct",
        path: "/products/create",
        component: ProductCreateView,
        beforeEnter: () => {
            if (window.role != window.roles.ROLE_ADMIN) {
                return '/PageNotFound'
            }
        }
    },
];

const router = createRouter({
    routes,
    history: createWebHistory(process.env.BASE_URL),
});

export default router;