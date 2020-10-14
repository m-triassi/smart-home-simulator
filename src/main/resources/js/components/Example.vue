<template>
    <button v-on:click="count++">You clicked me {{ count }} times.</button>
</template>

<script>
export default {
        name: 'example',
// Im leaving this here because usually we'll use props, but commented because you cant / shouldn't define the same thing in props and data()
//        props: {
//            count: Number
//        },
        data() {
            return {
                count: 0
            };
        },
        mounted() {
          axios.get('/users').then(function (response) {
            console.log(response);
          })
        }
}

</script>