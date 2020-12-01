<template>
  <div>
    <textarea
      class="output_box"
      v-model="message"
      @input="$emit('message', $event.target.value)"
      placeholder=""
      :readonly="true"
    ></textarea>
  </div>
</template>

<script>
export default {
  name: 'outputconsole',
  props: ['simulationEnabled'],
  data() {
    return {};
  },
  computed: {
    message() {
      return this.$store.state.outputMessage;
    }
  },
  mounted() {}
};
</script>

<style>
.output_box {
  width: 80%;
  height: auto;
}
</style>
