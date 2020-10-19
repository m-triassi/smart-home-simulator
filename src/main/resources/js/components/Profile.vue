<template>

    <div>

        <table class="profile_table">

            <tr>
                <toggle-button :value="true"
               :labels="{checked: 'On', unchecked: 'Off'}"/>
            </tr>

            <tr>
                <a href="edit/profile" class="EditButton">
                    Edit Profile
                </a>
            </tr>

            <tr>
                <img class="default_profile_avatar" src="https://www.pikpng.com/pngl/m/80-805523_default-avatar-svg-png-icon-free-download-264157.png" alt="Default Profile"/>
                <h5>Signed in user name: {{user.name}}</h5>
                <h5>Access type: {{user.role}}</h5>
            </tr>
                
            <tr>
                <h5>Location:</h5>
                <h5>Kitchen</h5>
            </tr>
                
            <tr>
                <h5><strong>Outside Temp. {{temperature}}C</strong></h5>
            </tr>

            <tr>
                <h5><strong>{{input_date}}</strong></h5>
            </tr>

        </table>

    </div>

</template>

<script>

import Vue from 'vue';
import { ToggleButton } from 'vue-js-toggle-button'
 
Vue.component('ToggleButton', ToggleButton)

export default {
        name: 'profile',
        data() {
            return {

                temperature:15,
                input_date:new Date().toLocaleString(),
                user: {}
            };
        },

        methods: {
            getName() {
                var path = 'user/current';

                axios.get(path).then(response =>{
                    this.user = response.data;
                })
                .catch(function(error){
                    console.log(error);
                });
            }
        },
        mounted(){
            this.getName()
        }
}

</script>

<style>

    .profile_table{
        width: 100%;
        height: 100%;
    }

    .default_profile_avatar{
        width: 20%;
        height: auto;
    }

</style>