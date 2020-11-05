<template>
  <div>
    <table class="profile_table" v-if="user.id != undefined">
      <tr>
        <a href="edit/profile" class="EditButton" :class="{disabled: simulationEnabled}">
          Edit Profile
        </a>
      </tr>
      <tr>
        <img class="default_profile_avatar"
             src="https://www.pikpng.com/pngl/m/80-805523_default-avatar-svg-png-icon-free-download-264157.png"
             alt="Default Profile"/>
        <h5>Signed in user name: {{ user.name }}</h5>
        <h5>Access type: {{ user.role }}</h5>
      </tr>
      <tr>
        <h5>Location:</h5>
        <h5>{{user.zone.name}}</h5>
      </tr>
      <tr>
        <h5><strong>Outside Temp. {{ this.user.home.outside_temp }}C</strong></h5>
      </tr>
      <tr>
        <h5><strong>{{ new Date().toLocaleString() }}</strong></h5>
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
  props: ["user", "simulationEnabled"],
  data() {
    return {
    };
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