<template>
    <div>
        <a href="login" class="LoginButton"> Login </a>
        <a href="signup" class="SignupButton"> Signup </a>
        <a href="logout" class="LogoutButton"> Logout </a>

        <table class="main_table">
            <tr>
                <td class="profile_section" rowspan="2">
                    <p>Simulation</p>
                    <toggle-button
                        :sync="true"
                        :value="Boolean(this.$store.state.simulationState)"
                        :labels="{ checked: 'On', unchecked: 'Off' }"
                        @change="changeState()"
                        class="onOffSimul"
                        :disabled="$store.state.user.name == null"
                    />
                    <profile></profile>
                </td>
                <td>
                    <p>Modules</p>
                    <modules></modules>
                </td>
                <td>
                    <div v-for="zone in this.$store.state.zones" :key="zone.id" class="zone_box">
                      <p>{{ zone.name }}</p>
                      <p>Users in this zone: {{ zone.users.map(user => { return user.name }).join(", ") }}</p>
                      <p></p>
                    </div>
                    <p>House Layout</p>
                </td>
            </tr>
            <td colspan="2">
                <p>Output Console</p>
                <outputconsole></outputconsole>
            </td>
            <tr></tr>
        </table>
    </div>
</template>

<script>
import profile from './Profile';
import modules from './Modules';
import outputconsole from './OutputConsole';

export default {
    name: 'mainpage',
    components: {
        profile: profile,
        modules: modules,
        outputconsole: outputconsole,
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
                            this.$store.state.user = response.data;
                            if (this.$store.state.user.zone.id == 0) {
                                this.$store.state.isAway = true;
                            } else {
                                this.$store.state.isAway = false;
                            }
                            if (this.$store.state.simulationState == null || this.$store.state.simulationState == undefined) {
                                this.$store.state.simulationState = this.$store.state.user.home.simulationState;
                            }
                        });
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        getZones() {
          if(this.$store.state.user.home){
            axios
                .get('/zones?home_id=' + this.$store.state.user.home.id)
                .then((response) => {
                  this.$store.state.zones = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
          }
        },
        onToggle() {
          
          this.simulationEnabled = !this.simulationEnabled;
          
          if(this.simulationEnabled){

            if(!window.location.href.includes('#shc')){
              window.location.href = window.location.origin + '#shc';
            }
          }
          
        },
        changeState() {

            var speedselected = document.querySelector('span[id="speedselected"]').textContent.split(" ")[1];
            if (this.$store.state.simulationState != null) {
                if (this.$store.state.simulationState == 0) {
                    this.$store.state.simulationState = 1;
                    this.$store.state.user.home.simulationState = 1;
                    this.$store.commit('appendMessage', 'Simulation ON');
                    this.$store.state.simulationStart = Date.now()
                    axios.post('/home/update?simulation_state=0&id=' + this.$store.state.user.home.id, )
                        .catch(function (error) {
                          console.log(error);
                        });
                } else {
                    this.$store.state.simulationState = 0;
                    this.$store.state.user.home.simulationState = 0;
                    this.$store.commit('appendMessage', 'Simulation OFF');
                    axios.post('/home/update?simulation_state=0&id=' + this.$store.state.user.home.id +
                        '&start_time=' + this.$store.state.simulationStart +
                        '&multiplier=' + speedselected
                    ).then(response => this.$store.state.user.home.date = response.data.date).catch(function (error) {
                      console.log(error);
                    });
                }
            }
        },
    },
    mounted() {
        this.getUser();
        this.getZones()
    },
    data() {
        return {
            zones: {},
        };
    },
};
</script>

<style>
table,
th,
td {
    border: 1px solid black;
    border-collapse: collapse;
}

th,
td {
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