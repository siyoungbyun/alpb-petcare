<template>
  <div class="min-h-screen bg-gray-50 pt-20">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <h1 class="text-3xl font-bold text-gray-900 mb-8">
        펫시터 서비스 목록
      </h1>

      <!-- Loading State -->
      <div v-if="isLoading" class="text-center py-12">
        <p class="text-gray-500">로딩 중...</p>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded-lg">
        {{ error }}
      </div>

      <!-- Service List -->
      <div v-else class="space-y-4">
        <div
          v-for="service in services"
          :key="service.id"
          class="bg-white rounded-lg shadow-sm p-6 flex items-center justify-between"
        >
          <!-- Service Info -->
          <div class="grid grid-cols-5 gap-8 flex-grow">
            <div>
              <div class="text-sm text-gray-500 mb-1">서비스 이름</div>
              <div class="font-medium">{{ service.name }}</div>
            </div>

            <div>
              <div class="text-sm text-gray-500 mb-1">펫시터 이름</div>
              <div class="font-medium">{{ service.owner.name }}</div>
            </div>

            <div>
              <div class="text-sm text-gray-500 mb-1">서비스 종류</div>
              <div class="font-medium">{{ service.serviceType || '미정' }}</div>
            </div>

            <div>
              <div class="text-sm text-gray-500 mb-1">가격</div>
              <div class="font-medium">{{ service.price.toLocaleString() }}원</div>
            </div>

            <div>
              <div class="text-sm text-gray-500 mb-1">서비스 가능 요일</div>
              <div class="font-medium">{{ formatDaysOfWeek(service.availableDaysOfWeek) }}</div>
            </div>
          </div>

          <!-- Action Button -->
          <button
            class="px-6 py-2 rounded-lg"
            :class="service.isAvailable ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'"
          >
            {{ service.isAvailable ? '활성' : '비활성' }}
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
import { api } from '../services/api'

export default {
  name: 'PetServiceList',
  data() {
    return {
      services: [],
      isLoading: true,
      error: null
    }
  },
  methods: {
    formatDaysOfWeek(days) {
      if (!days || !Array.isArray(days)) return '없음'

      const dayMap = {
        'MONDAY': '월',
        'TUESDAY': '화',
        'WEDNESDAY': '수',
        'THURSDAY': '목',
        'FRIDAY': '금',
        'SATURDAY': '토',
        'SUNDAY': '일'
      }

      return days.map(day => dayMap[day]).join(', ')
    }
  },
  async created() {
    try {
      const services = await api.getPetServices()
      this.services = services
    } catch (err) {
      this.error = err.message
      console.error('Failed to fetch services:', err)
    } finally {
      this.isLoading = false
    }
  }
}
</script>