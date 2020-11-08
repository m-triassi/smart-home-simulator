<template>
    <div>
        <p>This is the core module</p>
        <table class="module_table">
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
                picked:"None",
                locations:{},
                checkedLocation: "None",
                openingsList:{}
            };
        },
        methods: {
            getZones() {
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
    }
</style>