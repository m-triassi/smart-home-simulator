<template>
  <div>
    <p>This is the house layout</p>

    <div
      v-for="zone in this.$store.state.zones"
      :key="zone.id"
      class="zone_box"
    >
      <p>{{ zone.name }}</p>
      <p>
        Users in this zone:
        {{
          zone.users
            .map(user => {
              return user.name;
            })
            .join(', ')
        }}
      </p>
      <span>Openings in this zone:</span>
      <table>
        <tr>
          <td
            v-for="(open, index) in zone.openings.map(opening => {
              return displayOpening(opening);
            })"
            :key="index"
          >
            <span :key="index" v-html="open"></span>
          </td>
        </tr>
      </table>
      <span>Lights in this zone:</span>
      <div
        v-for="(open, index) in zone.appliances.map(appliance => {
          return displayLights(appliance);
        })"
        :key="index"
      >
        <span :key="index" v-html="open"></span>
      </div>
      <p></p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'houselayout',
  data() {
    return {};
  },
  methods: {
    getUser() {
      var path = 'user/current';
      axios
        .get(path)
        .then(response => {
          axios.get('/user?id=' + response.data.id).then(response => {
            this.$store.state.user = response.data;
            if (this.$store.state.user.zone.id == 0) {
              this.$store.state.isAway = true;
            } else {
              this.$store.state.isAway = false;
            }
            if (
              this.$store.state.simulationState == null ||
              this.$store.state.simulationState == undefined
            ) {
              this.$store.state.simulationState = this.$store.state.user.home.simulationState;
            }
          });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
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
    displayOpening(opening) {
      var result;

      if (opening.type == 'window') {
        if (opening.state == 0) {
          result =
            '<img class="icon" src="https://www.flaticon.com/svg/static/icons/svg/3190/3190083.svg">';
        } else {
          result =
            '<img class="icon" src="https://www.flaticon.com/svg/static/icons/svg/3190/3190150.svg ">';
        }
      } else {
        if (opening.state == 0) {
          result =
            '<img class="icon" src="https://www.flaticon.com/svg/static/icons/svg/32/32533.svg">';
        } else {
          result =
            '<img class="icon" src="https://www.flaticon.com/svg/static/icons/svg/59/59801.svg ">';
        }
      }
      return result;
    },
    displayLights(appliance) {
      var result;
      if (appliance.state == 0) {
        result =
          '<img class="icon" src="https://www.flaticon.com/svg/static/icons/svg/82/82648.svg ">';
      } else {
        result =
          '<img class="icon" src="https://www.flaticon.com/svg/static/icons/svg/3721/3721106.svg ">';
      }
      return result;
    }
  },
  computed:{
      count(){
        return this.$store.state.houseLayoutKey
      }
  },
  watch:{

    count(newCount, oldCount){

      console.log("The key count in now "+newCount)
      this.$forceUpdate();

    }

  }
};
</script>
