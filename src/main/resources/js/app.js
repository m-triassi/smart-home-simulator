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
import { library } from '@fortawesome/fontawesome-svg-core'
import { faUserSecret } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

library.add(faUserSecret)
Vue.component('font-awesome-icon', FontAwesomeIcon)

// make axios globally available
window.axios = axios

const store = new VueX.Store({
  state: {
    outputMessage: "",
    isAway: false,
    user: {},
    zones: [],
    simulationState: null,
    simulationStart: null,
  },
  mutations: {
    appendMessage(state, message) {
      state.outputMessage += (message + "\n");
    },
    changeAwayState(state) {
      state.isAway = !state.isAway;
    },
    changeSimulationState(state){
      state.simulationState = !state.simulationState;
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
              if (response.data.zone.id == 0) {
                this.$store.state.isAway = true;
              } else {
                this.$store.state.isAway = false;
              }
              this.$store.state.simulationState = response.data.house.simulation_state;
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

