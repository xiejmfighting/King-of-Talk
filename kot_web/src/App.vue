<template>
  <div>
    <NavBar></NavBar>
  </div>
  <router-view />
</template>

<script>
// import $ from 'jquery'
import NavBar from './components/NavBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"
export default {
  components: {
    NavBar,
  },
  setup(){

   
    

  },

}
</script>

<style>
body {
  background-image: url("./assets/background.jpg");
  background-size: cover;
}
</style>
