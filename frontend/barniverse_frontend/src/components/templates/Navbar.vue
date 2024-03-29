<template>
    <nav class="navbar navbar-expand-lg navbar-light navbarStyle">
        <div class="navbar-brand">
            <router-link id="image" class="navbarBrand" to="/">
                <img src="logo_transparent_zugeschnitten.png" width="60" height="60" class="d-inline-block align-bottom" alt="Logo from Barniverse">
            </router-link>
        </div>
        <ul class="navbar-nav nav nav-tabs">
            <li class="nav-item">
                <router-link id="headline" class="nav-link headline navbarBrand" to="/">
                    Barniverse
                </router-link>
            </li>
        </ul>
        <button class="navbar-toggler ml-auto collapseButton" data-bs-toggle="collapse" data-bs-target="#navContent" aria-controls="navContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse d-flex" id="navContent">
            <ul class="navbar-nav nav nav-tabs">
                <li class="nav-item">
                    <!-- dashboard -->
                    <router-link id="home" class="nav-link navItemStyle" to="/">
                        Home
                    </router-link>
                </li>
                <li class="nav-item">
                    <!-- available products -->
                    <router-link id="products" class="nav-link navItemStyle" to="/products">
                        Products
                    </router-link>
                </li>
                <li class="nav-item">
                    <!-- available auctions -->
                    <router-link id="auctions" class="nav-link navItemStyle" to="/auctions">
                        Auctions
                    </router-link>
                </li>
                <li v-if="isAdmin || isUser" class="nav-item">
                    <!-- my auctions I created and the placed offers for this auction -->
                    <router-link id="myAuctions" class="nav-link navItemStyle" to="/myAuctions">
                        My Auctions
                    </router-link>
                </li>
                <li v-if="isAdmin || isUser" class="nav-item">
                    <!-- my offers I placed -->
                    <router-link id="myOffers" class="nav-link navItemStyle" to="/myOffers">
                        My Offers
                    </router-link>
                </li>
                <li v-if="isAdmin" class="nav-item">
                    <!-- my offers I placed -->
                    <router-link id="user" class="nav-link navItemStyle" to="/user">
                        User
                    </router-link>
                </li>
            </ul>
            <ul id="userContent" class="navbar-nav nav nav-tabs flex-fill">
                <!-- OBSERVER - logged out -->
                <!-- register new user -->
                <li v-if="isObserver" id="userContentItem" class="nav-item ms-auto">
                    <router-link id="register" class="nav-link navItemStyle" to="/register">
                        Register
                    </router-link>
                </li>
                <!-- login -->
                <li v-if="isObserver" class="nav-item navIcon">
                    <router-link id="login" class="nav-link navItemStyle" to="/login">
                        <i class="bi bi-box-arrow-in-right" alt="Login"></i>
                    </router-link>
                </li>

                <!-- USER/ADMIN - logged in -->
                <!-- my username -->
                <li v-if="isAdmin || isUser" id="userContentItem" class="nav-item ms-auto">
                    <router-link id="profile" class="nav-link navItemStyle" to="/profile">
                        {{ getUsername }}
                    </router-link>
                </li>
                <!-- logout -->
                <li v-if="isAdmin || isUser" class="nav-item navIcon" @click="logout">
                    <router-link id="logout" class="nav-link navItemStyle" to="/">
                        <i class="bi bi-box-arrow-right" alt="Logout"></i>
                    </router-link>
                </li>
            </ul>
        </div>
    </nav>
</template>

<script>
export default {
    name: "Navbar",
    mounted() {
        // set correct style for changing between mobile and desktop mode
        window.$(window).on('resize', function() {
            var minScreenWidth = 992;
            if (window.$(document).height() > window.$(window).height()) { // check if scrollbar is visible
                minScreenWidth -= 17;
            }
            if (window.$(window).width() < minScreenWidth) {
                window.$("#navContent").removeClass("d-flex");
                window.$("#userContent").removeClass("flex-fill");
                window.$("#userContentItem").removeClass("ms-auto");
            } else {
                window.$("#navContent").addClass("d-flex");
                window.$("#userContent").addClass("flex-fill");
                window.$("#userContentItem").addClass("ms-auto");
            }
        });
    },
    methods: {
        logout() {
            sessionStorage.removeItem("jwt-token");
            window.event.emit("reloadJWT");
        }
    },
    computed: {
        getUsername() {
            return this.$store.state.username;
        },
        isAdmin() {
            return this.$store.getters.isAdmin
        },
        isUser() {
            return this.$store.getters.isUser
        },
        isObserver() {
            return this.$store.getters.isObserver
        }
    },
    watch:{
        $route (to) {
            if (to.name != "imprint" && to.name != "help") {
                var id = "#" + to.name
                this.$nextTick(() => {
                    window.$(".navItemStyleActive").removeClass("navItemStyleActive");
                    window.$(id).addClass("navItemStyleActive");
                })
            }
        }
    }
}
</script>

<style>
.navbarStyle {
    padding: 10px !important;
}

.navItemStyle {
    color: #4b3535 !important;
}

.navItemStyleActive {
    border-color: #dee2e6 #dee2e6 #fff !important;
    background-color: white !important;
}

.collapseButton {
    margin-left: auto;
}

.headline {
    color: #4b3535 !important;
    font-size: 18pt !important;
    font-weight: bold !important;
    font-family: monospace;
    padding: 1px !important;
    padding-right: 50px !important;
    margin: 0px !important;
    border-color: rgba(0, 0, 0, 0) !important;
}

.headline:hover {
    border-color: rgba(0, 0, 0, 0) !important;
}

.headline:active {
    border-color: rgba(0, 0, 0, 0) !important;
}

/* standard navbar */
@media (min-width: 992px) {
    .navIcon {
        align-self: center;
        margin-left: 20px;
        margin-right: 20px;
    }
}

/* mobile navbar */
@media (max-width: 991px) {
    .nav-tabs {
        --bs-nav-tabs-border-color: rgba(0, 0, 0, 0) !important;
    }
    .navItemStyle.navItemStyleActive {
        background-color: #ebdbc7 !important;
        border-color: rgba(0, 0, 0, 0) !important;
    }
    .navItemStyle:hover {
        background-color: #ebdbc7 !important;
        border-color: rgba(0, 0, 0, 0) !important;
    }
    .navItemStyle {
        border-radius: 0.375rem !important;
        padding-left: 10px !important;
    }
}
</style>