<template>
    <div>
        <p>This is the core module</p>
        <h5
            class="centerText"
            v-if="Boolean(this.$store.state.simulationState)"
        >
            Simulation ON
            <span
                class="enableSimulTimer"
                v-if="Boolean(this.$store.state.simulationState)"
                >{{ $store.state.user.home.date }}</span
            >
        </h5>
        <h5
            class="centerText"
            v-if="!Boolean(this.$store.state.simulationState)"
        >
            Simulation OFF
            <span
                class="disableSimulTimer"
                v-if="!Boolean(this.$store.state.simulationState)"
                >{{ $store.state.user.home.date }}</span
            >
        </h5>

        <table class="module_table">
            <tr>
                Simulation speed
            </tr>
            <tr>
                <div
                    v-bind:key="speed.id"
                    v-for="speed in speeds"
                    :disabled="Boolean($store.state.simulationState)"
                >
                    <input
                        type="radio"
                        :id="speed.id"
                        :value="speed.value"
                        v-model="speedSelected"
                        :disabled="Boolean($store.state.simulationState)"
                    />
                    <label :for="speed.id">{{ speed.value }}</label>
                    <br />
                </div>
                <span id="speedselected">Selected: {{ speedSelected }}</span>
            </tr>
            <tr>
                Item
            </tr>
            <tr>
                <div v-bind:key="item.id" v-for="item in items">
                    <input
                        type="radio"
                        :id="item.id"
                        :value="item"
                        v-model="picked"
                    />
                    <label :for="item.id">{{ item.name }}</label>
                    <br />
                </div>
                <span>Selected: {{ picked.name }}</span>
            </tr>
        </table>
        <table class="module_table">
            <tr>
                Open/close
            </tr>
            <tr>
                <div v-bind:key="location.id" v-for="location in locations">
                    <input
                        type="radio"
                        :id="location.id"
                        :value="location"
                        v-model="checkedLocation"
                    />
                    <label :for="location.id">{{ location.name }}</label>
                    <br />
                </div>
                <span>Selected: {{ checkedLocation.name }}</span>
                <br />
                <button v-on:click="toggleHouseItemState()">All/None</button>
                <span>{{ $store.state.houseItemsList }}</span>
            </tr>
        </table>
    </div>
</template>
<script>
export default {
    name: 'core',
    data() {
        return {
            items: [
                {
                    id: 1,
                    name: 'Window',
                    type: 'window',
                    houseItemType: 'opening',
                },
                {
                    id: 2,
                    name: 'Light',
                    type: 'light',
                    houseItemType: 'appliance',
                },
                {
                    id: 3,
                    name: 'Door',
                    type: 'door',
                    houseItemType: 'opening',
                },
            ],
            speeds: [
                {
                    id: 1,
                    value: '0.5',
                    type: 1,
                },
                {
                    id: 2,
                    value: '1',
                    type: 2,
                },
                {
                    id: 3,
                    value: '2',
                    type: 3,
                },
                {
                    id: 4,
                    value: '5',
                    type: 4,
                },
                {
                    id: 5,
                    value: '10',
                    type: 5,
                },
            ],
            picked: 'None',
            speedSelected: 1,
            locations: {},
            checkedLocation: 'None',
            houseItemsSize: 0,
        };
    },
    methods: {
        getZones() {
            if (this.$store.state.user.home) {
                var path = 'zones?home_id=' + this.$store.state.user.home.id;
                axios
                    .get(path)
                    .then((response) => {
                        this.locations = response.data;
                        console.log(this.locations);
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }
        },
        getOpenings() {
            var path =
                'openings?zone_id=' +
                this.checkedLocation.id +
                '&type=' +
                this.picked.type;
            // axios
            //     .get(path)
            //     .then((response) => {
            //         this.$store.state.houseItemsList = response.data;
            //         console.log(
            //             'size of array in getOpenings(): ' +
            //                 this.$store.state.houseItemsList.length
            //         ); //size 2
            //     })
            //     .catch(function (error) {
            //         console.log(error);
            //     });

            this.$store.state.houseItemsList = this.callAxios(path);

            console.log(
                'size of array in getOpenings() at the end: ' +
                    this.$store.state.houseItemsList.length
            ); //size 0
        },
        toggleHouseItemState() {
            if (this.picked.houseItemType == 'opening') {
                this.getOpenings();
                console.log(
                    'size of array in toggle: ' +
                        this.$store.state.houseItemsList.length
                );
                for (var houseItem in this.$store.state.houseItemsList) {
                    this.houseItemsSize += 1;
                    if (houseItem.state == 0) {
                        axios
                            .post(
                                '/openings/update?id=' +
                                    houseItem.id +
                                    '&state=' +
                                    1
                            )
                            .then(function (response) {})
                            .catch(function (error) {
                                console.log(error);
                            });
                    } else {
                        axios
                            .post(
                                '/openings/update?id=' +
                                    houseItem.id +
                                    '&state=' +
                                    0
                            )
                            .then(function (response) {})
                            .catch(function (error) {
                                console.log(error);
                            });
                    }
                }
                this.$store.commit(
                    'appendMessage',
                    'Changed the state of ' +
                        this.houseItemsSize +
                        ' ' +
                        this.picked.name +
                        '(s)'
                );
            }
        },
        callAxios(path) {
            axios
                .get(path)
                .then(function (response) {
                    console.log(response);
                    return response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
    },
    mounted() {
        setTimeout(this.getZones, 1000);
    },
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
    margin: auto;
}
</style>