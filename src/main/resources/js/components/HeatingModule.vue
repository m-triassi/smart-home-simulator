<template>
  <div>
    <p>This is the heating module</p>
    <table>
      <tr class="automaticModeTable">
        <td class="automaticModeTable">Automatic mode set for</td>
        <td class="automaticModeTable">
          <h5 v-if="isSummerPeriod()" class="cooling">Cooling</h5>
          <h5 v-if="!isSummerPeriod()" class="heating">Heating</h5>
        </td>
      </tr>
    </table>
    <br />
    <br />
    <table>
      <tr>
        <td>Room ID</td>
        <td>Group ID</td>
        <td>Name</td>
        <td>Temperature</td>
        <td>Overwritten</td>
      </tr>
      <tr v-bind:key="zone.id" v-for="zone in $store.state.zones">
        <td>{{ zone.id }}</td>
        <td>
          <input
            type="text"
            name="group"
            :id="zone.id"
            size="3"
            :placeholder="zone.group.id"
            v-model="inputGroup[zone.id]"
            @input="changeGroup($event, inputGroup[zone.id])"
          />
        </td>
        <td>{{ zone.name }}</td>
        <td>
          <input
            type="text"
            name="ow_temp"
            :id="zone.id"
            size="3"
            :placeholder="zone.temperature"
            v-model="inputTemp[zone.id]"
            @input="changeRoomTemperature($event, inputTemp[zone.id])"
          />
        </td>
        <td>
          <h5 v-if="overwrittenZones[zone.id] == 1">OW</h5>
          <h5 v-else-if="$store.state.zones[zone.id - 1].overridden == 1">
            OW
          </h5>
          <button
            v-if="overwrittenZones[zone.id] == 1"
            :id="zone.id"
            v-on:click="restore($event)"
          >
            Restore</button
          ><button
            v-else-if="$store.state.zones[zone.id - 1].overridden == 1"
            :id="zone.id"
            v-on:click="restore($event)"
          >
            Restore
          </button>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  name: 'heating',
  data() {
    return {
      inputTemp: [],
      inputGroup: [],
      overwrittenZones: [],
      currentMonth: this.$store.state.user.home.date.split('-')[1],
    };
  },
  methods: {
    getZones() {
      if (this.$store.state.user.home) {
        axios
          .get('/zones?home_id=' + this.$store.state.user.home.id)
          .then(response => {
            this.$store.state.zones = response.data;
            console.log('514zones ', this.$store.state.zones);
          })
          .catch(function (error) {
            console.log(error);
          });
      }
    },
    isSummerPeriod() {
      this.$store.state.currentMonth = parseInt(
        this.$store.state.user.home.date.split('-')[1]
      );
      console.log(
        'Inside heating module, month is ' + this.$store.state.currentMonth
      );
      
      return (this.currentMonth >= 3 && this.currentMonth <= 7);
    },
    changeRoomTemperature(e, inputTemp) {
      console.log(
        'set the temperature for zone' + e.target.id + ' to ' + inputTemp
      );

      this.overwrittenZones[e.target.id] = 1;

      console.log('overwrittenZones ' + this.overwrittenZones);

      axios
        .post(
          '/zone/update?zone_id=' +
            e.target.id +
            '&temperature=' +
            inputTemp +
            '&overridden=1'
        )
        .then(function (response) {})
        .catch(function (error) {
          console.log(error);
        });
    },
    changeGroup(e, inputGroup) {
      console.log('set the group for zone' + e.target.id + ' to ' + inputGroup);

      axios
        .post('/zone/update?zone_id=' + e.target.id + '&group_id=' + inputGroup)
        .then(function (response) {})
        .catch(function (error) {
          console.log(error);
        });
    },
    setOWZones() {
      for (var i = 0; i < this.$store.state.zones.length; i++) {
        console.log('i=' + i);
        console.log('OWZones: ' + this.$store.state.zones[i].overridden);
        console.log(
          'state var overridden: ' + this.$store.state.zones[i].overridden
        );
        this.overwrittenZones[i + 1] = this.$store.state.zones[i].overridden;
      }
    },
    restore(e) {
      this.overwrittenZones[e.target.id] = 0;

      this.$store.state.zones[e.target.id - 1].overridden = 0;

      axios
        .post('/zone/update?zone_id=' + (e.target.id) + '&overridden=0')
        .then(function (response) {})
        .catch(function (error) {
          console.log(error);
        });
    }
  },
  mounted() {
    setTimeout(this.setOWZones, 1200);
  }
};
</script>
<style>
.automaticModeTable {
  border: none;
}
.heating {
  color: red;
}
.cooling {
  color: blue;
}
</style>
