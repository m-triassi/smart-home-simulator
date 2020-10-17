<template>
    <div>
        <textarea class="output_box" v-model="message" placeholder="" :readonly="true"></textarea>
    </div>
</template>

<script>

export default {
        name: 'outputconsole',
        data() {
            return {
                message:"basifbnsaidfbisdbfsdf \n oanfdioabfisehfisebfsif \n oahf9sadhf9sdghs9dpofhasiopfghsdb \n abfnioseadfghsi9dfhs \n\n\n\n\n\n oiasgbdhfiasgbfdhs",
            };
        }
}

</script>

<style>

.output_box{
    width: 80%;
    height: auto;
}

</style>