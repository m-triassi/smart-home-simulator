<template>
    
    <div>

        <table class="main_table">

            <tr>
                <td rowspan="2">
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
                </td>
            <tr>

            </tr>

        </table>


    </div>

</template>

<script>

import profile from './Profile';

import modules from './Modules'

export default {
    name: 'mainpage',
    components:{
        'profile':profile,
        'modules':modules,
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
</style>