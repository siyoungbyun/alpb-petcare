<template>
  <div class="min-h-screen flex items-center justify-center bg-gray-50">
    <div class="text-center">
      <h1 class="text-4xl font-bold text-gray-800 mb-4">404</h1>
      <p class="text-xl text-gray-600">페이지를 찾을 수 없습니다.</p>
      <router-link to="/" class="mt-6 inline-block px-4 py-2 bg-orange-500 text-white rounded-lg hover:bg-orange-600">
        홈으로 돌아가기
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: 'NotFound'
}
</script>

<style scoped>
/* Your styles here */
</style>