window.Vue = require('vue');


import axios from 'axios';
import example from './components/Example';

// make axios globally available
window.axios = axios

const app = new Vue({
    el: '#app',
    components: {
        example,
    }
});

