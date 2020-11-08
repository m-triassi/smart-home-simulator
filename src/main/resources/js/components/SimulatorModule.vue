<template>
  <div>
    <table class="main_table">
      <tr>
        <p>This is the simulator module</p>
      </tr>
      <tr>
        <td>
          <edithome :simulationEnabled="simulationEnabled"></edithome>
        </td>
        <td>
          <tr>
            <strong>Move</strong>
            <select v-model="selectedUser">
              <option disabled value="">Select user</option>
              <option v-for="item in usersList" :key="item.id" :value="item">
                {{ item.name }}
              </option>
            </select>
          </tr>
          <tr>
            <strong>to</strong>
            <select v-model="selectedZone">
              <option disabled value="">Select zone</option>
              <!-- <option :key="0" :value="{id:0}">Leave</option> -->
              <option v-for="item in zonesList" :key="item.id" :value="item">
                {{ item.name }}
              </option>
            </select>
          </tr>
          <tr>
            <button v-on:click="moveUser()">Confirm</button>
          </tr>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import edithome from "./Edithome";

export default {
  name: "simulator",
  props: ["simulationEnabled"],
  components: {
    edithome: edithome,
  },
  data() {
    return {
      selectedUser: "",
      selectedZone: "",
      usersList: {},
      zonesList: {}
    };
  },
  methods: {
    getUsers() {
      var path = "users";
      axios
        .get(path)
        .then((response) => {
          this.usersList = response.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    getZones() {

      if(this.$store.state.user.home){
        var path = "zones?home_id=" + this.$store.state.user.home.id;
        axios
          .get(path)
          .then((response) => {
            this.zonesList = response.data;
          })
          .catch(function (error) {
            console.log(error);
          });
      }

    },
    moveUser() {
      var path =
        "user/update?id=" +
        this.selectedUser.id +
        "&zone_id=" +
        this.selectedZone.id;
      this.callAxios(path);

      if(this.selectedZone.id == 0){
        this.$store.commit('appendMessage',this.selectedUser.name+' left the house.');
      }else{
        this.$store.commit('appendMessage',this.selectedUser.name+' moved to '+this.selectedZone.name+'.');
      }

    },
    callAxios(path) {
      axios
        .post(path)
        .then(function (response) {
          console.log(response);
        })
        .catch(function (error) {
          console.log(error);
        });
    },
  },
  mounted() {
    this.getUsers();
    setTimeout(this.getZones, 1000);
  }
};
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
  width: 20%;
  height: auto;
}

.shs {
  width: 20%;
  height: auto;
}

.profile_section {
  width: 20%;
  height: auto;
}
</style>