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

const store = new VueX.Store({
  state: {
    outputMessage: "",
    isAway: false,
    user:{}
  },
  mutations: {
    appendMessage(state, message) {
      state.outputMessage += (message + "\n");
    },
    changeAwayState(state) {
      state.isAway = !state.isAway;
    }
  }
})

const app = new Vue({
  el: '#app',
  data: {
    checkedLocations: []
  },
  vuetify,
  store: store,
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
  methods: {

    getUser() {
      var path = 'user/current';
      axios
        .get(path)
        .then((response) => {
          axios
            .get('/user?id=' + response.data.id)
            .then((response) => {
              if (response.data.zone.id == undefined) {
                this.$store.state.isAway = true;
              } else {
                this.$store.state.isAway = false;
              }
            });
        })
        .catch(function (error) {
          console.log(error);
        });
    }

  },
  mounted() {

  }

}).use(VueX).$mount('#app');

