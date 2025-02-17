<template>
  <div class="min-h-screen bg-gray-50 pt-20">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <h1 class="text-2xl font-bold text-gray-900 mb-8">예약 관리</h1>

      <!-- Loading State -->
      <div v-if="isLoading" class="text-center py-12">
        <p class="text-gray-500">로딩 중...</p>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded-lg">
        {{ error }}
      </div>

      <!-- Booking List -->
      <div v-else class="space-y-6">
        <div v-for="booking in bookings" :key="booking.id"
          class="bg-white rounded-lg shadow-sm p-6 border border-gray-200">
          <div class="flex justify-between items-start">
            <div class="space-y-2">
              <div class="flex items-center space-x-2">
                <h3 class="text-lg font-medium text-gray-900">
                  {{ booking.petServiceName || '서비스 이름 없음' }}
                </h3>
                <span :class="getStatusClass(booking.status)"
                      class="px-2 py-1 text-xs font-medium rounded-full">
                  {{ getStatusText(booking.status) }}
                </span>
              </div>
              <p class="text-gray-600">
                예약자: {{ booking.userName }}
              </p>
              <p class="text-gray-600">
                예약 날짜: {{ formatDate(booking.reservationDate) }}
              </p>
              <p class="text-gray-600">
                예약 시간: {{ formatTimeString(booking.startTime) }} - {{ formatTimeString(booking.endTime) }}
              </p>
            </div>

            <!-- Action Buttons -->
            <div v-if="booking.status === 'PENDING'" class="space-x-3">
              <button
                @click="handleConfirm(booking, true)"
                class="px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors"
              >
                승인
              </button>
              <button
                @click="handleConfirm(booking, false)"
                class="px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-600 transition-colors"
              >
                거절
              </button>
            </div>
          </div>
        </div>

        <!-- Empty State -->
        <div v-if="bookings.length === 0" class="text-center py-12 bg-white rounded-lg">
          <p class="text-gray-500">예약 내역이 없습니다.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { api } from '../services/api'

export default {
  name: 'BookingManagement',
  setup() {
    const bookings = ref([])
    const isLoading = ref(true)
    const error = ref(null)

    const formatDate = (dateString) => {
      return new Date(dateString).toLocaleDateString('ko-KR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      })
    }

    const formatTimeString = (timeString) => {
      // Handle "HH:mm:ss" format
      return timeString.substring(0, 5) // Returns just "HH:mm"
    }

    const getStatusText = (status) => {
      const statusMap = {
        'PENDING': '대기중',
        'CONFIRMED': '승인됨',
        'REJECTED': '거절됨'
      }
      return statusMap[status] || status
    }

    const getStatusClass = (status) => {
      const statusClassMap = {
        'PENDING': 'bg-yellow-100 text-yellow-800',
        'CONFIRMED': 'bg-green-100 text-green-800',
        'REJECTED': 'bg-red-100 text-red-800'
      }
      return statusClassMap[status] || ''
    }

    const handleConfirm = async (booking, confirm) => {
      try {
        await api.confirmBooking(booking.petServiceId, booking.id, confirm)
        await fetchBookings()
        alert(confirm ? '예약이 승인되었습니다.' : '예약이 거절되었습니다.')
      } catch (err) {
        console.error('Failed to update booking:', err)
        alert(err.message || '예약 상태 변경에 실패했습니다.')
      }
    }

    const fetchBookings = async () => {
      try {
        isLoading.value = true
        const response = await api.getPetsitterBookings()
        bookings.value = response
      } catch (err) {
        console.error('Failed to fetch bookings:', err)
        error.value = err.message || '예약 목록을 불러오는데 실패했습니다.'
      } finally {
        isLoading.value = false
      }
    }

    onMounted(fetchBookings)

    return {
      bookings,
      isLoading,
      error,
      formatDate,
      formatTimeString,
      getStatusText,
      getStatusClass,
      handleConfirm
    }
  }
}
</script>