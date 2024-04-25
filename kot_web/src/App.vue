<template>
  <div>
    {{ content }}
  </div>
  <router-view />
</template>

<script>
import $ from 'jquery';
import { ref } from 'vue';

export default {
  name: "App",
  setup: () => {
    let content = ref("");
    $.ajax({
      url: "http://127.0.0.1:3000/pk/getbotinfo/",
      type: "get",
      success: resp => {
        content.value = resp
      }
    })
    return { content }
  }
}
</script>

<style>
body {
  background-image: url("./assets/background.jpg");
  background-size: cover;
}
</style>
