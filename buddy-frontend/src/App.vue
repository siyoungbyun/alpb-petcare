<script setup>
import NavBar from './components/NavBar.vue'
</script>

<template>
  <div class="min-h-screen bg-gray-50">
    <NavBar />
    <router-view></router-view>
  </div>
</template>

<style>
.app {
  width: 100%;
  min-height: 100vh;
  background-color: #f8f9fa;
}
</style>
