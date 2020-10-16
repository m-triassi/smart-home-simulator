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

