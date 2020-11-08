<template>
  <div class="signup-form">
    <form @submit.prevent="createHome()">
      <h3>Create Home</h3>
      <div class="signup-box">
        <input class='input' id='home_name' v-model='name' type="text" placeholder="Home name"
               :disabled="Boolean(this.$store.state.simulationState)"/>
      </div>

      <button class="btn" :disabled="Boolean(this.$store.state.simulationState)">Create Home</button>
    </form>
    <form @submit.prevent="joinHome()">
      <h3>Join Home</h3>

      <div class="signup-box">
        <input class='input' id='join_home_id' v-model='joinId' type="text" placeholder="Home ID"
               :disabled="Boolean(this.$store.state.simulationState)"/>
      </div>

      <button class="btn" :disabled="Boolean(this.$store.state.simulationState)">Join Home</button>
    </form>
    <form @submit.prevent="editHome()">
      <h3>Edit Home</h3>

      <div class="signup-box">
        <input class='input' id='home_id' v-model='id' type="text" placeholder="Home id" :disabled="Boolean(this.$store.state.simulationState)"/>
      </div>

      <div class="signup-box">
        <input class='input' id='home_temperature' v-model='temperature' type="text" placeholder="Outside temperature"
               :disabled="Boolean(this.$store.state.simulationState)"/>
      </div>

      <div class="signup-box">
        <input class='input' id='home_date' v-model='date' type="date" placeholder="Date"
               :disabled="Boolean(this.$store.state.simulationState)"/>
      </div>

      <div class="signup-box">
        <input id='home_time' v-model='time' type="text"
               :placeholder="[[today.getHours() + ':' + today.getMinutes() + ':' + today.getSeconds()]]"
               :disabled="Boolean(this.$store.state.simulationState)">
      </div>
      <br/>
      <button class="btn" :disabled="Boolean(this.$store.state.simulationState)">Edit Home</button>
    </form>
    <form @submit.prevent="createZone" enctype="multipart/form-data" action="/zones/load">
      <h3>Create Zone</h3>

      <div class="signup-box">
        <input :disabled="Boolean(this.$store.state.simulationState)" class='input' name='home_id' id='zone_home_id' v-model='home_id' type="text"
               placeholder="Home id in which this zone is located"/>
      </div>

      <div class="signup-box">
<!--        <input class='input' name="layout" id='zone_layout' type="file" placeholder="Zone layout"/>-->
        <input :disabled="Boolean(this.$store.state.simulationState)" class='input' name="layout" id='zone_layout' type="file" placeholder="Zone layout" @change="processFile($event)"/>
      </div>

      <button class="btn" :disabled="Boolean(this.$store.state.simulationState)">Create Zone</button>
    </form>
    <br/>
  </div>
</template>

<script>
export default {
  name: 'edithome',
  data() {

    return {
      name: '',
      id: '',
      temperature: '',
      date: '',
      home_id: '',
      layout: '',
      joinId: '',
      today: new Date(),
      time: new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds()
    }
  },
  methods: {
    createHome() {
      var path = '/home/store?name=' + this.name;
      this.callAxios(path);
    },
    editHome() {
      var path = '/home/update?id=' + this.id + '&temperature=' + this.temperature + '&date=' + this.date + ' ' + this.time;
      this.callAxios(path);
    },
    createZone(e) {
      console.log(this.layout)
      axios.post("/zones/load?home_id=" + this.home_id, {layout: this.layout}, {
        headers: {"Content-Type": "multipart/form-data; boundary=---011000010111000001101001"}
      })
    },
    joinHome() {
      this.callAxios('/user/update?id=' + this.$store.state.user.id + '&home_id=' + this.joinId)
    },
    callAxios(path) {
      axios.post(path).then(function (response) {
        window.location.href = "/";
      }).catch(function (error) {
        console.log(error);
      });
    },
    processFile(event) {
      this.layout = event.target.files[0]
    }
  }
}
</script>