<template>
  <div class="min-h-screen bg-gray-50 pt-20">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <h1 class="text-3xl font-bold text-gray-900 mb-6">
        펫시터 서비스 예약
      </h1>

      <!-- Service Selection -->
      <div class="bg-white rounded-lg shadow-sm p-6 mb-8">
        <h2 class="text-xl font-semibold text-gray-800 mb-6">서비스 선택</h2>
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          <div
            v-for="service in services"
            :key="service.name"
            class="border rounded-lg p-4 cursor-pointer hover:border-blue-500 transition-colors"
          >
            <h3 class="font-medium text-lg mb-2">{{ service.name }}</h3>
            <p class="text-gray-600 text-sm">{{ service.description }}</p>
          </div>
        </div>
      </div>

      <!-- Booking Calendar -->
      <div class="bg-white rounded-lg shadow-sm p-6">
        <h2 class="text-xl font-semibold text-gray-800 mb-6">예약 일정</h2>
        <div class="text-center py-8 text-gray-500">
          서비스를 선택하면 예약 가능한 일정이 표시됩니다
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ServiceBooking',
  data() {
    return {
      services: [
        {
          name: '산책',
          description: '전문 펫시터와 함께하는 산책 서비스'
        },
        {
          name: '돌봄',
          description: '집에서 편안하게 돌봐드립니다'
        },
        {
          name: '미용',
          description: '전문가의 미용 서비스'
        }
      ]
    }
  }
}
</script>