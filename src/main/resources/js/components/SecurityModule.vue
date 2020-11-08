<template>
    <div>
        <table class="module_table">
            <tr>
                <p>This is the security module</p>
            </tr>
            <tr>
                <div>
                    <toggle-button
                        :value="this.$store.state.isAway"
                        :labels="{ checked: 'Away', unchecked: 'At Home' }"
                        @change="changeState()"
                        :width="100"
                        :switch-color="{
                            checked: '#F2E708',
                            unchecked: '#08F208',
                        }"
                    />
                </div>
            </tr>
        </table>
    </div>
</template>

<script>
export default {
    name: 'security',
    props: ['simulationEnabled'],
    data() {
        return {
            zonesList: {},
        };
    },
    methods: {
        changeState() {
            this.$store.commit('changeAwayState');
            var path = '';
            if (this.$store.state.isAway) {
                path =
                    'user/update?id=' +
                    this.$store.state.user.id +
                    '&zone_id=' +
                    0;
                this.$store.commit(
                    'appendMessage',
                    this.$store.state.user.name +
                        ' left the house. This user is now [Away]'
                );
                this.$store.state.user.zone.id = 0;
            } else {
                path =
                    'user/update?id=' +
                    this.$store.state.user.id +
                    '&zone_id=' +
                    1;
                this.$store.commit(
                    'appendMessage',
                    this.$store.state.user.name +
                        ' entered the house. This user is now [At Home]'
                );
                this.$store.state.user.zone = this.zonesList[0];
            }

            this.callAxios(path);
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
        getZones() {
            if(this.$store.state.user.home){
                var path = 'zones?home_id=' + this.$store.state.user.home.id;
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
    },
    mounted() {
    setTimeout(this.getZones, 1000);
  }
};
</script>

<style>
table,
tr,
td {
    border: 1px solid black;
    border-collapse: collapse;
}

.module_table {
    width: 50%;
    height: auto;
}
</style>