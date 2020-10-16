window.Vue = require('vue');

import profile from './components/Profile';
import mainpage from './components/MainPage';
import modules from './components/Modules';
import core from './components/CoreModule';
import security from './components/SecurityModule';
import heating from './components/HeatingModule';
import custom from './components/CustomModule';
import simulator from './components/SimulatorModule';

import Vue from 'vue'
import vuetify from 'vuetify' // path to vuetify export
import axios from 'axios';
import example from './components/Example';
import login from './components/Login';
import signup from './components/Signup';

// make axios globally available
window.axios = axios

const app = new Vue({
    el: '#app',
    data:{
      checkedLocations:[]
    },
    vuetify,
    components: {
        mainpage,
        profile,
        modules,
        core,
        security,
        heating,
        custom,
        simulator,
    },
    
}).$mount('#app');

