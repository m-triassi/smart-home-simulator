window.Vue = require('vue');

import example from './components/Example';
import loginForm from './components/Login';
import signupForm from './components/Signup';

const app = new Vue({
    el: '#app',
    components: {
        example,
        loginForm,
        signupForm,
    }
});
