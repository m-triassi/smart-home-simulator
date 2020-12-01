<template>
  <div class="signup-form">
    <form @submit.prevent="submitForm()">
      <h3>Edit your profile</h3>

      <div class="signup-box">
        <input
          class="input"
          id="name"
          v-model="name"
          type="text"
          placeholder="Name"
        />
      </div>

      <div class="signup-box">
        <input
          class="input"
          id="email"
          v-model="email"
          type="email"
          placeholder="Email"
        />
      </div>

      <div class="signup-box">
        <input
          class="input"
          id="password"
          v-model="password"
          type="password"
          placeholder="Password"
        />
      </div>

      <div class="signup-box">
        <label>Are you a parent, a child or a guest?</label><br />
        <input
          class="radio"
          name="role"
          v-model="isParent"
          type="radio"
          value="true"
        />
        <label>Parent</label>
        <input
          class="radio"
          name="role"
          v-model="isChild"
          type="radio"
          value="true"
        />
        <label>Child</label>
        <input
          class="radio"
          name="role"
          v-model="isGuest"
          type="radio"
          value="true"
        />
        <label>Guest</label>
      </div>

      <button class="btn">Edit</button>
    </form>
    <br />
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: '',
      email: '',
      password: '',
      isParent: '',
      isChild: '',
      isGuest: ''
    };
  },

  methods: {
    submitForm() {
      if (this.isParent == '') this.isParent = false;
      if (this.isChild == '') this.isChild = false;
      if (this.isGuest == '') this.isGuest = false;
      var path = '/user/service?';
      axios
        .post(path)
        .then(function (response) {
          console.log(response);
          window.location.href = '/';
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  }
};
</script>
