<template>
    <div>
      <router-view/>
    </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  background-color: #d3d3d3;
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #0080ff00;
}
#poster {
  height: 100%;
  width: 100%;
  position: fixed;
  margin: 0;
  background-color: #f6f6f6;
  overflow: hidden;
}
body{
  margin: 0px;
  /* background: url("~@/assets/background.png"); */
  /*background-position: top;*/
  background-size: auto;
  background-color: #ffffff;
}
</style>
