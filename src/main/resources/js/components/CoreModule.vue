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
                    <input type="checkbox" :id="location.id" :value="location.name" v-model="checkedLocations">
                    <label :for="location.id">{{location.name}}</label>
                </div>

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
                        name:"Windows"
                    },
                    {
                        id:2,
                        name: "Lights"
                    },
                    {
                        id:3,
                        name:"Doors"
                    }

                ],
                picked:"None",

                locations:[

                    {
                        id:4,
                        name:"Backyard"
                    },
                    {
                        id:5,
                        name:"Garage"
                    },
                    {
                        id:6,
                        name:"Main"
                    },

                ],
                checkedLocations: [],

            };
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