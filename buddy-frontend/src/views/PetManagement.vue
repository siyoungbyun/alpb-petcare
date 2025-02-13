<template>
  <div class="min-h-screen bg-gray-50 pt-20">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <h1 class="text-3xl font-bold text-gray-900 mb-6">
        내 반려동물 관리
      </h1>

      <!-- Pet List Section -->
      <div class="bg-white rounded-lg shadow-sm p-6 mb-8">
        <div class="flex justify-between items-center mb-6">
          <h2 class="text-xl font-semibold text-gray-800">등록된 반려동물</h2>
          <button class="px-4 py-2 bg-gray-800 text-white rounded-md hover:bg-gray-700">
            반려동물 추가
          </button>
        </div>

        <!-- Empty State -->
        <div class="text-center py-12 border-2 border-dashed border-gray-300 rounded-lg">
          <p class="text-gray-500 mb-4">아직 등록된 반려동물이 없습니다</p>
          <button class="text-blue-600 hover:text-blue-800 font-medium">
            반려동물 등록하기
          </button>
        </div>
      </div>

      <!-- Pet Care History -->
      <div class="bg-white rounded-lg shadow-sm p-6">
        <h2 class="text-xl font-semibold text-gray-800 mb-6">돌봄 이력</h2>
        <div class="text-center py-8 text-gray-500">
          아직 돌봄 이력이 없습니다
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PetManagement'
}
</script>