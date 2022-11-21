<template>
    <div id ="backgroundRegister"  >
        <div class="row justify-content-center align-items-center h-100">
            <div class="col-12 col-lg-9 col-xl-6">
                <div class="shadow-2-strong" id ="cardRegister" >
                    <div class="card-body p-4 p-md-5">
                        <h3 class="mb-4 pb-2 pb-md-0 mb-md-5">Registration Form</h3>
                        <RegisterForm/>
                    </div>
                </div>
            </div>
        </div>
    </div>
   
</template>

<script>
import RegisterForm from '@/components/forms/RegisterForm.vue';

export default {
    name: "RegisterView",
    components: { RegisterForm }
}
</script>

<style>
#cardRegister{
border-radius: 15px;
border-style: none;
background-color: #ebdbc7;
}


/* @media (max-width:767px) { 
    #backgroundRegister{
    min-height:1500px;
    }
}

@media (min-width:768px) { 
    #backgroundRegister{
    min-height:1200px;
    }
} */
</style>