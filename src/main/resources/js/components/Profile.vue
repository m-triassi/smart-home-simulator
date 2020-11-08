<template>
  <div>
    <a href="edit/profile" class="EditButton" :class="{disabled: simulationEnabled}">
          Edit Profile
    </a>
    <table class="profile_table" v-if="this.$store.state.user.id != undefined">

      <tr>
        <td>
          <h5>Signed in as:</h5>
        </td>
        <td class="profileValue">
          <img class="default_profile_avatar"
              src="https://www.pikpng.com/pngl/m/80-805523_default-avatar-svg-png-icon-free-download-264157.png"
              alt="Default Profile"/>
              <p/>
              {{ $store.state.user.name }}
        </td>
      </tr>
      <tr>

        <td>
          <h5>Acces type:</h5>
        </td>
        <td class="profileValue">
          <h5>{{ $store.state.user.role }}</h5>
        </td>
      </tr>
      <tr>
        <td>
          <h5>Location:</h5>
        </td>
        <td class="profileValue">
          <h5 v-if="$store.state.user.zone.id != 0">{{$store.state.user.zone.name}}</h5>
          <h5 v-if="$store.state.user.zone.id == 0">Away</h5>
        </td>
      </tr>
      <tr>
        <td>
          <h5>Outside Temperature:</h5>
        </td>
        <td class="profileValue">
          <h5>{{ this.$store.state.user.home.outside_temp }}C</h5>
        </td>

      </tr>
      <tr>
        <td>
          <h5>Simulation date:</h5>
        </td>
        <td class="profileValue">
          <h5>{{ $store.state.user.home.date}}</h5>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>

import Vue from 'vue';
import {ToggleButton} from 'vue-js-toggle-button'

Vue.component('ToggleButton', ToggleButton)

export default {
  name: 'profile',
  props: ["simulationEnabled"],
  data() {
    return {
    };
  },
  mounted(){
  }
}

</script>

<style>

.profile_table {
  width: 100%;
  height: 100%;
}

.default_profile_avatar {
  width: 20%;
  height: auto;
}

.disabled {
  cursor: not-allowed;
  color: gray;
  pointer-events: none;
}

</style>