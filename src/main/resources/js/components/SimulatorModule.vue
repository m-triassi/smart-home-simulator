<template>

    <div>
        <table class="main_table">

            <tr>
                <p>This is the simulator module</p>
            </tr>
                
            <!--<tr>
                <a href="edit/profile" class="EditButton">
                    Edit Profile
                </a>
            </tr>

            <tr>
                <a href="edit/home" class="EditHomeButton">
                    Create or edit Home
                </a>
            </tr>-->

            <tr class='shs'>
                <edithome></edithome>
            </tr>

        </table>
    </div>

</template>

<script>

import edithome from './Edithome'

export default {
        name: 'simulator',
        components:{
            'edithome':edithome,
        },
        data() {
            return {
                
            };
        }
}

</script>

<style>
    table, th, td {
        border: 1px solid black;
        border-collapse: collapse;
    }
    
    th, td {
        padding: 5px;
        text-align: left;    
    }

    .main_table{
        width: 100%;
        height: 100%;
    }

    .shs{
        width: 40%;
        height: auto;
    }

    .profile_section{
        width: 20%;
        height: auto;
    }
</style>