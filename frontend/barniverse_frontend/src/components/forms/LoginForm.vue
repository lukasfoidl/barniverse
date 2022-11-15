<template>
    <div>
        
        <form  id="formLogin" >

            <h3 class="fw-normal mb-3 pb-3" style="letter-spacing: 1px;">Log in</h3>

            <div class="form-outline mb-4">
                <input type="email" id="email" class="form-control form-control-lg" v-model="form.values.email" @blur="validate('email')"/>
                <label class="form-label" for="form2Example18">Email </label>
            </div>

            <div class="form-outline mb-4">
                <input type="password" id="password" class="form-control form-control-lg" v-model="form.values.password" @blur="validate('password')"/>
                <label class="form-label" for="form2Example28">Password</label>
            </div>
            <div>
                <p v-if="!!form.errors.email" class="text-danger">
                    {{form.errors.email}}
                </p>
                <p v-if="!!form.errors.password" class="text-danger">
                    {{form.errors.password}}
                </p>
                                        
            </div>
            <div class="pt-1 mb-4">
                    <button class="btn btn-info btn-lg btn-block" type="button" v-on:click.prevent="login">Login</button>
            </div>

            <p class="small mb-5 pb-lg-2"><a class="text-muted" href="#!">Forgot password?</a></p>
            <p>Don't have an account? <a href="#!" class="link-info">Register here</a></p>

        </form>

                        


    </div>
</template>

<script>

import {  object, string } from "yup"

export default {
    name: "LoginForm", 
    data: () => ({
        form: {
            values: {
                
                
                email:"",
                password:"",
                

            },
            errors: {}
        }
    }),
    methods: {
        //Register Button
        async login() {
            
            console.log(this.form.values.email)
            console.log(this.form.values.password)
            loginFormSchema
                .validate(this.form.values, {abortEarly: true})
                .then(() => {
                    this.form.error = {
                         
                        
                        
                        email:"", 
                        password:"", 
                        
                    }
                })
                .catch((error) => {
                    error.inner.forEach(() => {
                        this.forms.errors[error.path] = error.message
                    })
                })
        },
        validate(field) {
            loginFormSchema
                .validateAt(field, this.form.values)
                .then(() => {
                    this.form.errors[field] = ""
                })
                .catch((error) => {
                    this.form.errors[field] = error.message
                })
        }
    }
    
    
}


const loginFormSchema = object().shape({
    
     
    email: string().email("Email must be valid!").required("Email is required!"), 
    password: string().min(8, "Password must be at least 8 Characters!").required("Password is required!"), 
    })
</script>

<style>
#formLogin{
    width: 23rem;
    padding: 1cm;
    background-color: #ebdbc7;
    border-radius: 25px;
}
</style>