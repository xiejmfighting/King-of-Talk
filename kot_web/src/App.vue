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

   
    // $.ajax({
    //   url:"http://localhost:3000/user/account/register/",
    //   type:"post",
    //   data:{
    //     username:"400lux",
    //     password:"123",
    //     confirmedPassword:"123",
    //   },
    //   success(resp){
    //     console.log(resp);
    //   },
    //   error(resp){
    //     console.log(resp);
    //   },
    // })

  },

}
</script>

<style>
body {
  background-image: url("./assets/background.jpg");
  background-size: cover;
}
</style>
