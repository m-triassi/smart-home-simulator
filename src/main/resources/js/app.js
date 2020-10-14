window.Vue = require('vue');


import axios from 'axios';
import example from './components/Example';
import login from './components/Login';
import signup from './components/Signup';

// make axios globally available
window.axios = axios

const app = new Vue({
    el: '#app',
    components: {
        example,
        login,
        signup,
    }
});

