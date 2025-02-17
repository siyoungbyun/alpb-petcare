<template>
  <div class="min-h-screen bg-gray-50 pt-20">
    <div class="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <!-- Loading State -->
      <div v-if="isLoading" class="text-center py-12">
        <p class="text-gray-500">로딩 중...</p>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded-lg">
        {{ error }}
      </div>

      <!-- Booking Form -->
      <div v-else class="bg-white rounded-xl shadow-md p-8">
        <h1 class="text-2xl font-bold text-gray-900 mb-8">서비스 예약</h1>

        <!-- Service Details -->
        <div class="space-y-6 mb-8">
          <div class="bg-gray-50 rounded-lg p-6">
            <h2 class="text-lg font-semibold text-gray-900 mb-4">서비스 정보</h2>
            <div class="space-y-3">
              <p class="text-gray-600">
                <span class="text-gray-500">서비스명:</span> {{ service?.name }}
              </p>
              <p class="text-gray-600">
                <span class="text-gray-500">서비스 종류:</span> {{ service?.serviceType }}
              </p>
              <p class="text-gray-600">
                <span class="text-gray-500">가격:</span> {{ service?.price?.toLocaleString() }}원
              </p>
              <p class="text-gray-600">
                <span class="text-gray-500">가능 요일:</span> {{ formatDays(service?.availableDays) }}
              </p>
              <p class="text-gray-600">
                <span class="text-gray-500">가능 시간:</span>
                {{ service?.allDay ? '24시간' : `${formatTime(service?.startTime)} - ${formatTime(service?.endTime)}` }}
              </p>
              <p class="text-gray-600">
                <span class="text-gray-500">가능 지역:</span> {{ service?.locations?.join(', ') }}
              </p>
              <p class="text-gray-600">
                <span class="text-gray-500">돌봄 가능:</span> {{ service?.animalTypes?.join(', ') }}
              </p>
            </div>
          </div>

          <!-- Time Selection -->
          <div class="space-y-4">
            <h2 class="text-lg font-semibold text-gray-900">예약 시간 선택</h2>
            <div class="grid grid-cols-2 gap-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">시작 시간</label>
                <input
                  v-model="bookingTime.startTime"
                  type="time"
                  :min="formatTime(service?.startTime)"
                  :max="formatTime(service?.endTime)"
                  class="block w-full px-4 py-2 rounded-lg border border-gray-300 focus:ring-orange-500 focus:border-orange-500"
                  :class="{ 'border-red-300': startTimeError }"
                  required
                />
                <p v-if="startTimeError" class="mt-1 text-sm text-red-600">
                  {{ startTimeError }}
                </p>
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">종료 시간</label>
                <input
                  v-model="bookingTime.endTime"
                  type="time"
                  :min="formatTime(service?.startTime)"
                  :max="formatTime(service?.endTime)"
                  class="block w-full px-4 py-2 rounded-lg border border-gray-300 focus:ring-orange-500 focus:border-orange-500"
                  :class="{ 'border-red-300': endTimeError }"
                  required
                />
                <p v-if="endTimeError" class="mt-1 text-sm text-red-600">
                  {{ endTimeError }}
                </p>
              </div>
            </div>
            <p v-if="timeRangeError" class="text-sm text-red-600">
              {{ timeRangeError }}
            </p>
          </div>

          <!-- Total Price -->
          <div class="bg-orange-50 rounded-lg p-4">
            <div class="flex justify-between items-center">
              <span class="text-gray-900 font-medium">총 결제 금액</span>
              <span class="text-xl font-bold text-orange-600">
                {{ calculateTotalPrice().toLocaleString() }}원
              </span>
            </div>
          </div>

          <!-- Submit Button -->
          <button
            @click="handlePayment"
            :disabled="!bookingTime.startTime || !bookingTime.endTime || startTimeError || endTimeError || timeRangeError"
            class="w-full py-3 bg-orange-500 text-white rounded-lg hover:bg-orange-600 transition-colors font-medium"
            :class="{ 'opacity-50 cursor-not-allowed': !bookingTime.startTime || !bookingTime.endTime || startTimeError || endTimeError || timeRangeError }"
          >
            결제하기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { api } from '../services/api'

export default {
  name: 'ServiceBooking',
  setup() {
    const route = useRoute()
    const router = useRouter()
    const service = ref(null)
    const isLoading = ref(true)
    const error = ref(null)
    const bookingTime = ref({
      startTime: '',
      endTime: ''
    })
    const startTimeError = ref('')
    const endTimeError = ref('')
    const timeRangeError = ref('')

    const formatDays = (days) => {
      if (!days) return ''
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

    const validateTime = () => {
      startTimeError.value = ''
      endTimeError.value = ''
      timeRangeError.value = ''

      const serviceStart = new Date(`2000-01-01T${formatTime(service.value?.startTime)}`)
      const serviceEnd = new Date(`2000-01-01T${formatTime(service.value?.endTime)}`)
      const selectedStart = new Date(`2000-01-01T${bookingTime.value.startTime}`)
      const selectedEnd = new Date(`2000-01-01T${bookingTime.value.endTime}`)

      if (selectedStart < serviceStart) {
        startTimeError.value = `시작 시간은 ${formatTime(service.value?.startTime)} 이후여야 합니다`
        return false
      }

      if (selectedEnd > serviceEnd) {
        endTimeError.value = `종료 시간은 ${formatTime(service.value?.endTime)} 이전이어야 합니다`
        return false
      }

      if (selectedEnd <= selectedStart) {
        timeRangeError.value = '종료 시간은 시작 시간보다 늦어야 합니다'
        return false
      }

      return true
    }

    const calculateTotalPrice = () => {
      if (!service.value?.price || !bookingTime.value.startTime || !bookingTime.value.endTime) {
        return 0
      }

      const start = new Date(`2000-01-01T${bookingTime.value.startTime}`)
      const end = new Date(`2000-01-01T${bookingTime.value.endTime}`)
      const hours = (end - start) / (1000 * 60 * 60)

      return service.value.price * hours
    }

    const fetchService = async () => {
      try {
        isLoading.value = true
        const serviceId = route.query.serviceId
        if (!serviceId) {
          throw new Error('서비스 ID가 없습니다.')
        }

        const services = await api.getAllPetServices()
        service.value = services.find(s => s.id === Number(serviceId))

        if (!service.value) {
          throw new Error('서비스를 찾을 수 없습니다.')
        }

        // Set default booking times
        bookingTime.value.startTime = formatTime(service.value.startTime)
        bookingTime.value.endTime = formatTime(service.value.endTime)
      } catch (err) {
        console.error('Failed to fetch service:', err)
        error.value = err.message || '서비스 정보를 불러오는데 실패했습니다.'
      } finally {
        isLoading.value = false
      }
    }

    const handlePayment = () => {
      if (!validateTime()) {
        return
      }

      // TODO: Implement payment logic
      console.log('Payment processing...', {
        serviceId: service.value.id,
        startTime: bookingTime.value.startTime,
        endTime: bookingTime.value.endTime,
        totalPrice: calculateTotalPrice()
      })
    }

    // Validate times whenever they change
    watch(() => bookingTime.value.startTime, validateTime)
    watch(() => bookingTime.value.endTime, validateTime)

    onMounted(() => {
      fetchService()
    })

    return {
      service,
      isLoading,
      error,
      bookingTime,
      startTimeError,
      endTimeError,
      timeRangeError,
      formatDays,
      formatTime,
      calculateTotalPrice,
      handlePayment
    }
  }
}
</script>