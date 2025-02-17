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

      <!-- Service Cards Grid -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <div
          v-for="service in services"
          :key="service.id"
          class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-shadow"
        >
          <div class="flex justify-between items-start mb-4">
            <h2 class="text-xl font-semibold text-gray-900">{{ service.name }}</h2>
            <span class="text-lg font-medium text-orange-600">
              {{ service.price.toLocaleString() }}원
            </span>
          </div>

          <div class="space-y-2 text-sm">
            <p class="text-gray-600">
              <span class="text-gray-500">서비스 종류:</span> {{ service.serviceType }}
            </p>
            <p class="text-gray-600">
              <span class="text-gray-500">가능 요일:</span> {{ formatDays(service.availableDays) }}
            </p>
            <p class="text-gray-600">
              <span class="text-gray-500">가능 시간:</span>
              {{ service.allDay ? '24시간' : `${formatTime(service.startTime)} - ${formatTime(service.endTime)}` }}
            </p>
            <p class="text-gray-600">
              <span class="text-gray-500">가능 지역:</span> {{ service.locations.join(', ') }}
            </p>
            <p class="text-gray-600">
              <span class="text-gray-500">돌봄 가능:</span> {{ service.animalTypes.join(', ') }}
            </p>
          </div>

          <p class="mt-4 text-gray-600 line-clamp-2">{{ service.description }}</p>

          <!-- 예약 Button for other petsitters' services -->
          <div v-if="service.petsitterId !== userProfile?.id" class="mt-4">
            <button
              @click="handleBooking(service)"
              class="w-full py-2 bg-orange-500 text-white rounded-lg hover:bg-orange-600 transition-colors"
            >
              예약
            </button>
          </div>
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
import { ref, onMounted, computed } from 'vue'
import { api } from '../services/api'
import { useRouter } from 'vue-router'

export default {
  name: 'PetServiceList',
  setup() {
    const router = useRouter()
    const services = ref([])
    const isLoading = ref(true)
    const error = ref(null)
    const userProfile = ref(null)

    const formatDays = (days) => {
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

    const formatTime = (isoTime) => {
      if (!isoTime) return ''
      const date = new Date(isoTime)
      return date.toLocaleTimeString('ko-KR', { hour: '2-digit', minute: '2-digit', hour12: false })
    }

    const fetchUserProfile = async () => {
      try {
        userProfile.value = await api.getMyProfile()
      } catch (err) {
        console.error('Failed to fetch user profile:', err)
      }
    }

    const fetchServices = async () => {
      try {
        isLoading.value = true
        const response = await api.getAllPetServices()
        services.value = response
      } catch (err) {
        console.error('Failed to fetch services:', err)
        error.value = '서비스 목록을 불러오는데 실패했습니다.'
      } finally {
        isLoading.value = false
      }
    }

    const handleBooking = (service) => {
      // Navigate to booking page with service details
      router.push({
        path: '/service-booking',
        query: { serviceId: service.id }
      })
    }

    onMounted(() => {
      fetchServices()
      fetchUserProfile()
    })

    return {
      services,
      isLoading,
      error,
      userProfile,
      handleBooking,
      formatDays,
      formatTime
    }
  }
}
</script>