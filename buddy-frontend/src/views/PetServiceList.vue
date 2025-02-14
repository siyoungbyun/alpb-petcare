<template>
  <div class="min-h-screen bg-gray-50 pt-20">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <h1 class="text-3xl font-bold text-gray-900 mb-8">
        펫시터 서비스 목록
      </h1>

      <!-- Service List -->
      <div class="space-y-4">
        <div
          v-for="service in services"
          :key="service.id"
          class="bg-white rounded-lg shadow-sm p-6 flex items-center justify-between"
        >
          <!-- Service Info -->
          <div class="grid grid-cols-5 gap-8 flex-grow">
            <div>
              <div class="text-sm text-gray-500 mb-1">이름</div>
              <div class="font-medium">{{ service.name }}</div>
            </div>

            <div>
              <div class="text-sm text-gray-500 mb-1">서비스 종류</div>
              <div class="font-medium">{{ service.serviceType }}</div>
            </div>

            <div>
              <div class="text-sm text-gray-500 mb-1">가격</div>
              <div class="font-medium">{{ service.price.toLocaleString() }}원</div>
            </div>

            <div>
              <div class="text-sm text-gray-500 mb-1">서비스 가능 요일</div>
              <div class="font-medium">{{ service.availableDays }}</div>
            </div>
          </div>

          <!-- Action Button -->
          <button
            class="px-6 py-2 rounded-lg"
            :class="service.isAvailable ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'"
          >
            {{ service.isAvailable ? '활성' : '활성' }}
          </button>
        </div>
      </div>

      <!-- Navigation Buttons -->
      <div class="flex justify-between mt-8">
        <button
          class="px-4 py-2 text-orange-500 hover:bg-orange-50 rounded-lg transition-colors"
          @click="$router.push('/pet-service-registration')"
        >
          이전
        </button>
        <button
          class="px-4 py-2 bg-orange-500 text-white hover:bg-orange-600 rounded-lg transition-colors"
          @click="$router.push('/pet-service-registration')"
        >
          다음
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PetServiceList',
  data() {
    return {
      services: [
        {
          id: 1,
          name: '철수의 돌봄 서비스',
          serviceType: '미용',
          price: 15000,
          availableDays: '월, 화, 수, 목, 금',
          isAvailable: true
        },
        {
          id: 2,
          name: '철수의 돌봄 서비스',
          serviceType: '미용',
          price: 15000,
          availableDays: '월, 화, 수, 목, 금',
          isAvailable: false
        },
        {
          id: 3,
          name: '철수의 돌봄 서비스',
          serviceType: '미용',
          price: 15000,
          availableDays: '월, 화, 수, 목, 금',
          isAvailable: true
        },
        {
          id: 4,
          name: '철수의 돌봄 서비스',
          serviceType: '미용',
          price: 15000,
          availableDays: '월, 화, 수, 목, 금',
          isAvailable: true
        }
      ]
    }
  }
}
</script>