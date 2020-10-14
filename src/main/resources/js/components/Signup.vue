<template>
    <div class="signup-form">
        <form @submit.prevent="submitForm()">
            <h3>Sign Up</h3>

            <div class="signup-box">
                <label>Name</label>
                <input id ='name' v-model='name' type="text"/>
            </div>

            <div class="signup-box">
                <label>Email address</label>
                <input id='email' v-model='email' type="email"/>
            </div>

            <div class="signup-box">
                <label>Password</label>
                <input id='password' v-model='password' type="password"/>
            </div>

            <button>Sign Up</button>
        </form>
        <br/>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                name: '',
                email: '',
                password: ''
            }
        },
        
        methods: {
            submitForm(){
                console.log('514');
                console.log('name: ', this.name, ' email: ', this.email, ' password: ', this.password);
                var path = '/user/store?name=' + this.name + '&email=' + this.email + '&password=' + this.password;
                axios.post(path).then(function(response){
                    console.log(response);
                }).catch(function(error){
                    console.log(error);
                });
            }
        }
    }
</script>