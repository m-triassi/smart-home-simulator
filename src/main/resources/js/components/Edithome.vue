<template>
    <div class="signup-form">
        <form @submit.prevent="createHome()">
            <h3>Create Home</h3>

            <div class="signup-box">
                <input class= 'input' id ='home_name' v-model='name' type="text" placeholder="Home name" :disabled="simulationEnabled"/>
            </div>

            <button class="btn" :disabled="simulationEnabled">Create Home</button>
        </form>
        <form @submit.prevent="editHome()">
            <h3>Edit Home</h3>

            <div class="signup-box">
                <input class= 'input' id ='home_id' v-model='id' type="text" placeholder="Home id" :disabled="simulationEnabled"/>
            </div>

            <div class="signup-box">
                <input class= 'input' id ='home_temperature' v-model='temperature' type="text" placeholder="Outside temperature" :disabled="simulationEnabled"/>
            </div>

            <div class="signup-box">
                <input class= 'input' id ='home_date' v-model='date' type="date" placeholder="Date" :disabled="simulationEnabled"/>
            </div>

            <div class="signup-box">
                <input id = 'home_time' v-model='time' type="text" :placeholder="[[today.getHours() + ':' + today.getMinutes() + ':' + today.getSeconds()]]" :disabled="simulationEnabled">
            </div>
            <br/>
            <button class="btn" :disabled="simulationEnabled">Edit Home</button>
        </form>
        <form @submit.prevent="createZone()">
            <h3>Create Zone</h3>

            <div class="signup-box">
                <input class= 'input' id ='zone_id' v-model='zone_id' type="text" placeholder="Home id in which this zone is located" :disabled="simulationEnabled"/>
            </div>

            <div class="signup-box">
                <input class= 'input' id ='zone_layout' type="file" placeholder="Zone layout" @change="processFile($event)" :disabled="simulationEnabled"/>
            </div>

            <button class="btn" :disabled="simulationEnabled">Create Zone</button>
        </form>
        <br/>
    </div>
</template>

<script>

    import edithome from './Edithome'

    export default {
        name:'edithome',
        props:['simulationEnabled'],
        data() {

            return {
                name: '',
                id: '',
                temperature: '',
                date: '',
                zone_id: '',
                layout: '',
                today: new Date(),
                time: new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds()
                
            }
        },
        
        methods: {
            createHome() {
                
                var path = '/home/store?name=' + this.name ;
                this.callAxios(path);
            },
            editHome() {
                
                var path = '/home/update?id=' + this.id + '&temperature=' + this.temperature + '&date=' +this.date+' '+this.time;
                this.callAxios(path);
            },
            createZone() {
                var path = '/zones/load?layout=' + this.layout + '&home_id=' +this.zone_id;
                console.log('514 ', this.layout);
                axios.post(path,{"layout" : this.layout},{headers: {"Content-Type":"multipart/form-data; boundary=99999999"}}).then(function(response){
                    console.log(response);
                    window.location.href = "/";

                }).catch(function(error){
                    console.log(error);
                });
            },
            callAxios(path) {
                axios.post(path).then(function(response){
                    console.log(response);
                    window.location.href = "/";

                }).catch(function(error){
                    console.log(error);
                });
            },
            processFile(event) {
                this.someData = event.target.files[0];
            }
        }
    }
</script>