<template>

    <div>

        <h5 class="centerText" v-if="simulationEnabled">Simulation ON <span class="enableSimulTimer" v-if="simulationEnabled">{{ $store.state.user.home.date }}</span></h5>
        <h5 class="centerText" v-if="!simulationEnabled">Simulation OFF <span class="disableSimulTimer" v-if="!simulationEnabled">{{ $store.state.user.home.date }}</span></h5>

        <table class="module_table">
            <tr>
                Simulation speed
            </tr>

            <tr>
                <div v-bind:key="speed.id" v-for="speed in speeds" :disabled="simulationEnabled">
                    <input type="radio" :id="speed.id" :value="speed.value" v-model="speedSelected" :disabled="simulationEnabled">
                    <label :for="speed.id">{{speed.value}}</label>
                    <br>
                </div>
                <span id="speedselected">Selected: {{ speedSelected }}</span>
                
            </tr>

            <tr>
                Item
            </tr>

            <tr>
                <div v-bind:key="item.id" v-for="item in items">
                    <input type="radio" :id="item.id" :value="item.name" v-model="picked">
                    <label :for="item.id">{{item.name}}</label>
                    <br>
                </div>
                <span>Selected: {{ picked }}</span>
                
            </tr>

        </table>

        <table class="module_table">

            <tr>
                Open/close
            </tr>

            <tr>
                <div v-bind:key="location.id" v-for="location in locations">
                    <input type="radio" :id="location.id" :value="location.name" v-model="checkedLocation">
                    <label :for="location.id">{{location.name}}</label>
                    <br>
                </div>

                <span>Selected: {{ checkedLocation }}</span>
                <br>
                <button>All/None</button>
                
            </tr>

        </table>

    </div>

</template>

<script>

export default {
        name: 'core',
        props: ["simulationEnabled"],
        data() {
            return {

                items:[

                    {
                        id:1,
                        name:"Windows",
                        type:1
                    },
                    {
                        id:2,
                        name: "Lights",
                        type:2
                    },
                    {
                        id:3,
                        name:"Doors",
                        type:3
                    }

                ],
                 speeds:[

                    {
                        id:1,
                        value:"0.5",
                        type:1
                    },
                    {
                        id:2,
                        value: "1",
                        type:2
                    },
                    {
                        id:3,
                        value:"2",
                        type:3
                    },
                    {
                        id:4,
                        value:"5",
                        type:4
                    },
                    {
                        id:5,
                        value:"10",
                        type:5
                    }

                ],
                picked:"None",
                speedSelected: 1,
                locations:{},
                checkedLocation: "None",
                openingsList:{}

            };
        },
        methods: {
            getZones() {
                if(this.$store.state.user.home){
                    var path = "zones?home_id=" + this.$store.state.user.home.id;
                    axios
                    .get(path)
                    .then((response) => {
                        this.locations = response.data;
                        console.log(this.locations)
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
                }
            },
            getOpenings(){
                var path = "openings";
                axios
                .get(path)
                .then((response) => {
                    this.openingsList = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
            },
            changeOpeningState(){
                for(var i = 0; i < this.openingsList.length; i++){
                    if(this.openingsList[i].state == 0){
                        this.openingsList[i].state = 1
                    }else{
                        this.openingsList[i].state = 0
                    }
                }
            }
        },
  mounted() {
    setTimeout(this.getZones, 1000);
  }
}

</script>

<style>
    table, tr, td {
        border: 1px solid black;
        border-collapse: collapse;
    }

    .module_table{
        width: 50%;
        height: auto;
        margin: auto;
    }
</style>