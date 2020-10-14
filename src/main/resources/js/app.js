window.Vue = require('vue');


import axios from 'axios';
import example from './components/Example';
import loginForm from './components/Login';
import signupForm from './components/Signup';

// make axios globally available
window.axios = axios

const app = new Vue({
    el: '#app',
    components: {
        example,
        loginForm,
        signupForm,
    }
});

