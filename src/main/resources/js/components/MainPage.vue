<template>
    
    <div>
        <a href="login" class="LoginButton">
            Login
        </a>
        <a href="signup" class="SignupButton">
            Signup
        </a>
        <a href="logout" class="LogoutButton">
            Logout
        </a>
        <a href="edit/profile" class="EditButton">
            Edit Profile
        </a>
        <a href="edit/home" class="EditHomeButton">
            Create or edit Home
        </a>
        <table class="main_table">

            <tr>
                <td class="profile_section" rowspan="2">
                    <p>Simulation</p>

                    <profile></profile>

                </td>

                <td>
                    <p>Modules</p>

                    <modules></modules>

                </td>

                <td>
                    <p>House View</p>
                </td>
            </tr>
                <td colspan="2">
                    <p>Output Console</p>

                    <outputconsole></outputconsole>

                </td>
            <tr>

            </tr>

        </table>
    </div>

</template>

<script>

import profile from './Profile';
import modules from './Modules'
import outputconsole from './OutputConsole'


export default {
    name: 'mainpage',
    components:{
        'profile':profile,
        'modules':modules,
        'outputconsole':outputconsole,
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

    .profile_section{
        width: 20%;
        height: auto;
    }
</style>