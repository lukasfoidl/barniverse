<template>
    <!-- Einleitung -->
    <div>
        <center><BarniverseJustificationText /></center>
        <p/>
        <center>Due to the company's headquarters in Austria, the imprint is in German.</center>
    </div>
    <p/>
    <!-- Firmenwortlaut -->
    <div>
        <center>Barniverse GmbH</center>
    </div>
    <!-- Rechtsform -->
    <div>
        <center>Gesellschaft mit beschränkter Haftung</center>
    </div>
    <!-- Unternehmensgegenstand -->
    <div>
        <center>Durchführung von Auktionen</center>
    </div>
    <p/>
    <!-- UID-Nummer -->
    <div>
        <center>UID-Nr.: ATU12345678</center>
    </div>
    <!-- Firmenbuchnummer -->
    <div>
        <center>FB-Nr.: 123456 a</center>
    </div>
    <!-- Firmenbuchgericht -->
    <div>
        <center>FB-Gericht.: Handelsgericht Wien</center>
    </div>
    <!-- Firmensitz -->
    <div>
        <center>Firmensitz: <Address /></center>
    </div>
    <p/>
    <!-- Telefon -->
    <div>
        <center>Tel.: <Phone /></center>
    </div>
    <!-- Email -->
    <div>
        <center>E-Mail:&nbsp;<a href=mailto:office@barniverse.com><Email /></a></center>
    </div>
    <p/>
    <!-- Mitgliedschaften bei WK Organisationen -->
    <div>
        <center>WKÖ, WK Wien</center>
    </div>
    <!-- anwendbare Rechtsvorschriften und Zugang dazu -->
    <div>
        <center>Rechtsvorschriften:&nbsp;<a href="https://www.ris.bka.gv.at">www.ris.bka.gv.at</a></center>
    </div>
    <!-- Aufsichtsbehörde -->
    <div>
        <center>Magistrat der Stadt Wien</center>
    </div>
    <p/>
    <!-- Berufsbezeichnung -->
    <!-- Verleihungsstaat -->
    <!-- Angaben zur Online Streitbeilegung -->
    <div>
        <center>Verbraucher haben die Möglichkeit, Beschwerden an die Online-Streitbeilegungsplattform der EU zu richten:&nbsp;<a href="https://ec.europa.eu/odr">www.ec.europa.eu/odr</a>. Sie können allfällige Beschwerden auch an die oben angegebene E-Mail-Adresse richten.</center>
    </div>

</template>

<script>
import Address from '@/components/text/Address.vue';
import BarniverseJustificationText from '@/components/text/BarniverseJustificationText.vue';
import Email from '@/components/text/Email.vue';
import Phone from '@/components/text/Phone.vue';

export default {
    name: "ImprintView",
    components: { BarniverseJustificationText, Phone, Email, Address }
}
</script>

<style>
</style>