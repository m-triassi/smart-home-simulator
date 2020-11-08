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
                    />
                    <profile></profile>
                </td>
                <td>
                    <p>Modules</p>
                    <modules></modules>
                </td>
                <td>
                    <div v-for="zone in zones" :key="zone.id" class="zone_box">
                        {{ zone.name }}
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
                            console.log("simulationState before if "+this.$store.state.simulationState)
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
            axios
                .get('/zones?home_id=' + this.$store.state.user.home.id)
                .then((response) => {
                    this.zones = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        changeState() {
            if (this.$store.state.simulationState != null) {
                if (this.$store.state.simulationState == 0) {
                    this.$store.state.simulationState = 1;
                    this.$store.state.user.home.simulationState = 1;
                    this.$store.commit('appendMessage', 'Simulation ON');
                    axios
                        .post(
                            '/home/update?id=' +
                                this.$store.state.user.home.id +
                                '&simulation_state=' +
                                1
                        )
                        .then(function (response) {})
                        .catch(function (error) {
                            console.log(error);
                        });
                } else {
                    this.$store.state.simulationState = 0;
                    this.$store.state.user.home.simulationState = 0;
                    this.$store.commit('appendMessage', 'Simulation OFF');
                    axios
                        .post(
                            '/home/update?id=' +
                                this.$store.state.user.home.id +
                                '&simulation_state=' +
                                0
                        )
                        .then(function (response) {})
                        .catch(function (error) {
                            console.log(error);
                        });
                }
            }
        },
    },
    mounted() {
        this.getUser();
        setTimeout(this.getZones, 1000);
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