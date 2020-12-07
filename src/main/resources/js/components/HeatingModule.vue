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
        <td>Overridden</td>
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
          <h5 v-if="overwrittenZones[zone.id] == 1">overridden</h5>
          <h5 v-else-if="$store.state.zones[zone.id - 1].overridden == 1">
            overridden
          </h5>
          <button
            v-if="overwrittenZones[zone.id] == 1"
            :id="zone.id"
            v-on:click="restore($event)"
          >
            Restore</button
          ><button
            v-else-if="$store.state.zones[zone.id - 1] && $store.state.zones[zone.id - 1].overridden == 1"
            :id="zone.id"
            v-on:click="restore($event)"
          >
            Restore
          </button>
        </td>
      </tr>
    </table>

    <form @submit.prevent="createGroup()">
      <h3>Create Group</h3>
      <div class="signup-box">
        <input
          class="input"
          id="group_name"
          v-model="name"
          type="text"
          placeholder="Group name"
          :disabled="Boolean(this.$store.state.simulationState)"
        />

        <input
          class="input"
          id="temperature"
          v-model="temperature"
          type="text"
          placeholder="Temperature (*C)"
          :disabled="Boolean(this.$store.state.simulationState)"
        />

        <input
          class="input"
          id="winter"
          v-model="winter"
          type="text"
          placeholder="Temperature Winter (*C)"
          :disabled="Boolean(this.$store.state.simulationState)"
        />

        <input
          class="input"
          id="summer"
          v-model="summer"
          type="text"
          placeholder="Temperature Summer (*C)"
          :disabled="Boolean(this.$store.state.simulationState)"
        />

        <input type="radio" id="heating" value="1" v-model="picked" />
        <label for="heating">Heating</label>
        <br />
        <input type="radio" id="cooling" value="0" v-model="picked" />
        <label for="cooling">Cooling</label>
        <br />
      </div>
      <button
        class="btn"
        :disabled="Boolean(this.$store.state.simulationState)"
      >
        Create Group
      </button>
    </form>
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
      currentMonth: this.$store.state.user.home.date.split('-')[1]
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

      return this.currentMonth >= 3 && this.currentMonth <= 7;
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
        .post('/zone/update?zone_id=' + e.target.id + '&overridden=0')
        .then(function (response) {})
        .catch(function (error) {
          console.log(error);
        });
    },
    createGroup() {
      var path =
        '/group/create?name=' +
        this.name +
        '&home_id=' +
        this.$store.state.user.home.id +
        '&temperature=' +
        this.temperature +
        '&winter=' +
        this.winter +
        '&summer=' +
        this.summer +
        '&heating=' +
        this.picked;
      axios
        .post(path)
        .then(function (response) {
          window.location.href = '/';
        })
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
