<script>
import NavBar from './components/NavBar.vue'

export default {
  name: 'App',
  components: {
    NavBar
  }
}
</script>

<template>
  <div id="app">
    <NavBar />
    <router-view />
  </div>
</template>

<style>
.app {
  width: 100%;
  min-height: 100vh;
  background-color: #f8f9fa;
}
</style>
