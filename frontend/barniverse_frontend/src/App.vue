<template>
    <nav class="navbar navbar-expand-lg navbar-light navbarCSS">
        <a class="navbar-brand" href="#">
            <img src="./images/logo_transparent_zugeschnitten.png" width="60" height="60" class="d-inline-block align-bottom" alt="Logo from barniverse">
            <span class="headlineCSS">
                Barniverse
            </span>
        </a>
        <button class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navContent" aria-controls="navContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navContent">
            <ul class="navbar-nav nav nav-tabs">
                <li class="nav-item">
                    <!-- dashboard -->
                    <a class="nav-link navItemCSS active" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <!-- available products -->
                    <a class="nav-link navItemCSS" href="#">Products</a>
                </li>
                <li class="nav-item">
                    <!-- available auctions -->
                    <a class="nav-link navItemCSS" href="#">Auctions</a>
                </li>
                <li class="nav-item">
                    <!-- auctions I created and the placed offers for this auction -->
                    <a class="nav-link navItemCSS" href="#">My Auctions</a>
                </li>
                <li class="nav-item">
                    <!-- my offers I placed-->
                    <a class="nav-link navItemCSS" href="#">My Offers</a>
                </li>
            </ul>
        </div>
    </nav>
</template>

<script>
</script>

<style>
.navbarCSS {
    padding: 10px !important;
}

.headlineCSS {
    color: #4b3535 !important;
    font-size: 18pt !important;
    font-weight: bold !important;
    font-family: monospace;
    padding-right: 50px !important;
}

.navItemCSS {
    color: #4b3535 !important;
}
</style>
