<template>
    <div class="login-form">
        <form @submit.prevent="submitForm()">
            <h3>Sign In</h3>

            <div class="login-box">
                <label>Email address</label>
                <input id="email" v-model="email" type="email"/>
            </div>

            <div class="login-box">
                <label>Password</label>
                <input id="password" v-model="password" type="password"/>
            </div>

            <button type="submit">Sign In</button>
        </form>
        <br/>
    </div>
</template>

<script>
    export default {
        name : 'login',
       data() {
            return {
                email: '',
                password: ''
            }
        },
        
        methods: {
            submitForm(){
                var path = '/userLogin?email=' + this.email + '&password=' + this.password;
                axios.get(path).catch(function(error){
                    console.log(error);
                });
            }
        }
    }
</script>