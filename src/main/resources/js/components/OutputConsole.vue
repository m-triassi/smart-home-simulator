<template>
    <div>
        <textarea class="output_box" v-model="message" @input="$emit('message', $event.target.value)" placeholder="" :readonly="true"></textarea>
    </div>
</template>

<script>

export default {
        name: 'outputconsole',
        props:['simulationEnabled', 'message'],
        data() {
            return {
            };
        },
        computed:{
            message(){
                return localStorage.message;
            }
        },
        mounted(){
            localStorage.message +="test"
        }
}

</script>

<style>

.output_box{
    width: 80%;
    height: auto;
}

</style>