<template>
  <div>
    <a href="login" class="LoginButton">
      Login
    </a>
    <a href="signup" class="SignupButton">
      Signup
    </a>
    <a href="logout" class="LogoutButton">
      Logout
    </a>

    <table class="main_table">

      <tr>
        <td class="profile_section" rowspan="2">
          <p>Simulation</p>

          <toggle-button :value="simulationEnabled" :labels="{checked: 'On', unchecked: 'Off'}"
                         @change="simulationEnabled=!simulationEnabled"/>

          <profile :simulationEnabled="simulationEnabled" :user="user"></profile>

        </td>

        <td>
          <p>Modules</p>

          <modules :simulationEnabled=simulationEnabled :user="user"></modules>

        </td>

        <td>
          <div v-for="zone in zones" :key="zone.id" class="zone_box">{{zone.name}}</div>
          <p>House Layout</p>
        </td>
      </tr>
      <td colspan="2">
        <p>Output Console</p>

        <outputconsole :simulationEnabled=simulationEnabled></outputconsole>

      </td>
      <tr>

      </tr>

    </table>
  </div>
   
</template>

<script>

import profile from './Profile';
import modules from './Modules'
import outputconsole from './OutputConsole'


export default {
  name: 'mainpage',
  components: {
    'profile': profile,
    'modules': modules,
    'outputconsole': outputconsole,
  },
  methods: {
    getUser() {
      var path = 'user/current';
      axios.get(path).then(response => {
        axios.get('/user?id=' + response.data.id).then(response => {
          this.user = response.data;
        })
      }).catch(function (error) {
        console.log(error);
      });
    },
    getZones() {
      axios.get("/zones?home_id=" + this.user.home.id).then(response => {
        this.zones = response.data;
      }).catch(function (error){
        console.log(error)
      })
    }
  },
  mounted() {
    this.getUser();
    setTimeout(this.getZones, 1000)
  },
  data() {
    return {
      simulationEnabled: false,
      user: {},
      zones: {}
    };
  }
}

</script>

<style>
table, th, td {
  border: 1px solid black;
  border-collapse: collapse;
}

th, td {
  padding: 5px;
  text-align: left;
}

.main_table {
  width: 100%;
  height: 100%;
}

.profile_section {
  width: 20%;
  height: auto;
}
.zone_box {
  padding: 100px;
  outline: 2px solid black;
}
</style>