window.Vue = require('vue');

import profile from './components/Profile';
import mainpage from './components/MainPage';
import modules from './components/Modules';
import core from './components/CoreModule';
import security from './components/SecurityModule';
import heating from './components/HeatingModule';
import custom from './components/CustomModule';
import simulator from './components/SimulatorModule';
import login from './components/Login';
import signup from './components/Signup';
import editprofile from './components/Editprofile';
import edithome from './components/Edithome';


import Vue from 'vue'
import VueX from 'vuex'
import vuetify from 'vuetify' // path to vuetify export
import axios from 'axios';


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
        signup,
        editprofile,
        edithome
    },
    
}).use(VueX).$mount('#app');

const store = new VueX.Store({
    state: {
        count: 0
    },
    mutations: {
        increment (state) {
            state.count++
        }
    }
})

