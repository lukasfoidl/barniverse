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
import AuctionCreateView from "@/views/AuctionCreateView"
import AuctionUpdateView from "@/views/AuctionUpdateView"
import ChangePasswordView from "@/views/ChangePasswordView"
import OfferCreateView from "@/views/OfferCreateView"
import OfferView from "@/views/OfferView"
import OfferDetailsView from "@/views/OfferDetailsView"
import store from "@/store"

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
        name: "createAuctions",
        path: "/auctions/create",
        component: AuctionCreateView,
        beforeEnter: () => {
            if (!store.getters.isUser && !store.getters.isAdmin) {
                return '/PageNotFound'
            }
        }
    },
    {
        name: "updateAuction",
        path: "/auctions/update/",
        component: AuctionUpdateView,
        beforeEnter: () => {
            if (!store.getters.isUser && !store.getters.isAdmin) {
                return '/PageNotFound'
            }
        }
    },
    {
        name: "myAuctions",
        path: "/myAuctions",
        component: MyAuctionsView,
        beforeEnter: () => {
            if (!store.getters.isUser && !store.getters.isAdmin) {
                return '/PageNotFound'
            }
        }
    },
    {
        name: "createOffers",
        path: "/offers/create",
        component: OfferCreateView,
        beforeEnter: () => {
            if (!store.getters.isUser && !store.getters.isAdmin) {
                return '/PageNotFound'
            }
        }
    },
    {
        name: "myOffers",
        path: "/myOffers",
        component: MyOffersView,
        beforeEnter: () => {
            if (!store.getters.isUser && !store.getters.isAdmin) {
                return '/PageNotFound'
            }
        }
    },
    {
        name: "offers",
        path: "/offers",
        component: OfferView,
        beforeEnter: () => {
            if (!store.getters.isUser && !store.getters.isAdmin) {
                return '/PageNotFound'
            }
        }
    },
    {
        name: "offerDetails",
        path: "/offers/details",
        component: OfferDetailsView,
        beforeEnter: () => {
            if (!store.getters.isUser && !store.getters.isAdmin) {
                return '/PageNotFound'
            }
        }
    },
    {
        name: 'user',
        path: '/user', 
        component: UserView,
        beforeEnter: () => {
            if (!store.getters.isAdmin) {
                return '/PageNotFound'
            }
        }
    },
    {
        name: "profile",
        path: "/profile",
        component: ProfileView,
        beforeEnter: () => {
            if (!store.getters.isUser && !store.getters.isAdmin) {
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
            if (!store.getters.isAdmin) {
                return '/PageNotFound'
            }
        }
    },
    {
        name: "createProduct",
        path: "/products/create",
        component: ProductCreateView,
        beforeEnter: () => {
            if (!store.getters.isAdmin) {
                return '/PageNotFound'
            }
        }
    },
    {
        name: "changePassword",
        path: "/users/changePassword",
        component: ChangePasswordView,
        beforeEnter: () => {
            if (!store.getters.isUser && !store.getters.isAdmin) {
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