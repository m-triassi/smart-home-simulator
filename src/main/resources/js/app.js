window.Vue = require('vue');

import example from './components/Example';

const app = new Vue({
    el: '#app',
    components: {
        example,
    }
});
