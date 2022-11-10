<template>
    <footer class="text-center text-lg-start footer textColor">
        <!-- Grid container -->
        <div class="container p-4 pb-0">
        <!-- Section: Links -->
            <section class="">
                <!--Grid row-->
                <div class="row">
                    <!-- Grid column -->
                    <div class="col-lg-4 col-xl-4 mx-auto mt-3">
                        <h6 class="text-uppercase mb-4 font-weight-bold">
                            Barniverse
                        </h6>
                        <p>
                            This is a fictitious company for a web engineering project at the UAS Technikum Wien.
                            All provided data is fictitious and for demonstration purposes only!
                            No transactions of any kind can be carried out on this website!  
                        </p>
                    </div>

                    <!-- Grid column -->
                    <div class="col-lg-1 col-xl-1 mx-auto mt-3">
                        <h6 class="text-uppercase mb-4 font-weight-bold">
                            Links
                        </h6>
                        <p>
                            <router-link id="help" class="nav-link textColor" to="/help">
                                Help
                            </router-link>
                        </p>
                        <p>
                            <router-link id="imprint" class="nav-link textColor" to="/imprint">
                                Imprint
                            </router-link>
                        </p>
                    </div>

                    <!-- Grid column -->
                    <div class="col-lg-3 col-xl-3 mx-auto mt-3">
                        <h6 class="text-uppercase mb-4 font-weight-bold">
                            Contact
                        </h6>
                        <p>
                            <a class="nav-link" href="https://www.google.at/maps/place/F+Building,+H%C3%B6chst%C3%A4dtpl.+6,+1200+Wien/@48.2390559,16.3759752,17z/data=!3m1!4b1!4m6!3m5!1s0x476d064f691978b1:0xe6bce6d69ea2874d!8m2!3d48.2390534!4d16.3775228!16s%2Fg%2F11bw40m9p0?hl=de">
                                <i class="bi bi-house mr-3"></i> Vienna, Höchstädtplatz 6, 1200, AT
                            </a>
                        </p>
                        <p>
                            <a class="nav-link" href="mailto:office@barniverse.com">
                                <i class="bi bi-envelope mr-3"></i> office@barniverse.com
                            </a>
                        </p>
                        <p><i class="bi bi-telephone mr-3"></i> +43 01 1234 567</p>
                    </div>

                    <!-- Grid column -->
                    <div class="col-lg-2 col-xl-2 mx-auto mt-3 followUsArea">
                        <h6 class="text-uppercase mb-4 font-weight-bold">Follow us</h6>

                        <!-- Facebook -->
                        <a class="btn btn-primary btn-floating m-1 facebook followMeLink" href="#" role="button">
                            <i class="bi bi-facebook" alt="Link to facebook page"></i>
                        </a>

                        <!-- Twitter -->
                        <a class="btn btn-primary btn-floating m-1 twitter followMeLink" href="#" role="button">
                            <i class="bi bi-twitter" alt="Link to twitter page"></i>
                        </a>

                        <!-- Google -->
                        <a class="btn btn-primary btn-floating m-1 google followMeLink" href="#" role="button">
                            <i class="bi bi-google" alt="Link to google search page"></i>
                        </a>

                        <!-- Instagram -->
                        <a class="btn btn-primary btn-floating m-1 instagram followMeLink" href="#" role="button">
                            <i class="bi bi-instagram" alt="Link to instagram page"></i>
                        </a>

                        <!-- Linkedin -->
                        <a class="btn btn-primary btn-floating m-1 linkedin followMeLink" href="#" role="button">
                            <i class="bi bi-linkedin" alt="Link to linkedin page"></i>
                        </a>

                        <!-- Github -->
                        <a class="btn btn-primary btn-floating m-1 github followMeLink" href="#" role="button">
                            <i class="bi bi-github" alt="Link to github repository"></i>
                        </a>
                    </div>
                </div>
            </section>
        </div>

        <!-- Copyright -->
        <div class="text-center p-3 copyrightArea">
            © 2022 Barniverse
        </div>
    </footer>
</template>

<script>
export default {
    name: "Footer"
}
</script>

<style>
.footer {
    background-color: #ebdbc7;
}

.textColor {
    color: #4b3535 !important;
}

.followUsArea {
    margin-bottom: 1rem !important;
}

.followMeLink {
    border: 0px !important;
    border-radius: 50% !important;
}

.facebook {
    background-color: #3b5998 !important;
}

.twitter {
    background-color: #55acee !important;
}

.google {
    background-color: #dd4b39 !important;
}

.instagram {
    background-color: #ac2bac !important;
}

.linkedin {
    background-color: #0082ca !important;
}

.github {
    background-color: #333333 !important;
}

.copyrightArea {
    background-color: rgba(0, 0, 0, 0.2);
}
</style>