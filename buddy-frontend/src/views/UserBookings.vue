<template>
    <div class="min-h-screen bg-gray-50 pt-20">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
            <h1 class="text-2xl font-bold text-gray-900 mb-8">내 예약 목록</h1>

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
                                    {{ booking.petServiceName }}
                                </h3>
                                <span :class="getStatusClass(booking.status)"
                                    class="px-2 py-1 text-xs font-medium rounded-full">
                                    {{ getStatusText(booking.status) }}
                                </span>
                            </div>
                            <p class="text-gray-600">
                                펫시터: {{ booking.userName }}
                            </p>
                            <p class="text-gray-600">
                                예약 날짜: {{ formatDate(booking.reservationDate) }}
                            </p>
                            <p class="text-gray-600">
                                예약 시간: {{ formatTimeString(booking.startTime) }} - {{ formatTimeString(booking.endTime)
                                }}
                            </p>
                            <p class="text-gray-900 font-medium">
                                총 결제 금액: {{ formatPrice(booking.totalPrice) }}원
                            </p>
                        </div>

                        <!-- Payment Button -->
                        <div v-if="booking.status === 'CONFIRMED'" class="ml-4">
                            <button @click="handlePayment(booking)"
                                class="px-4 py-2 bg-orange-500 text-white rounded-lg hover:bg-orange-600 transition-colors cursor-pointer">
                                결제하기
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
    name: 'UserBookings',
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
            return timeString.substring(0, 5) // Returns just "HH:mm"
        }

        const getStatusText = (status) => {
            const statusMap = {
                'PENDING': '대기중',
                'CONFIRMED': '승인됨',
                'REJECTED': '거절됨',
                'PURCHASED': '결제완료'
            }
            return statusMap[status] || status
        }

        const getStatusClass = (status) => {
            const statusClassMap = {
                'PENDING': 'bg-yellow-100 text-yellow-800',
                'CONFIRMED': 'bg-green-100 text-green-800',
                'REJECTED': 'bg-red-100 text-red-800',
                'PURCHASED': 'bg-blue-100 text-blue-800'
            }
            return statusClassMap[status] || ''
        }

        const formatPrice = (price) => {
            return price?.toLocaleString('ko-KR') || '0'
        }

        const handlePayment = async (booking) => {
            try {
                const IMP = window.IMP
                IMP.init('imp11840366')

                const merchantUid = `mid_${new Date().getTime()}_${booking.id}`

                const paymentData = {
                    pg: 'uplus',
                    pay_method: 'card',
                    merchant_uid: merchantUid,
                    name: booking.petServiceName,
                    amount: booking.totalPrice,
                    buyer_email: booking.userEmail,
                    buyer_name: booking.userName,
                    buyer_tel: booking.userPhone,
                }

                IMP.request_pay(paymentData, async function (response) {
                    console.log(response);
                    if (response.success) {
                        try {
                            // Send payment confirmation with all necessary data
                            await api.confirmTransaction(
                                booking.petServiceId,
                                booking.id,
                                {
                                    applyNum: response.apply_num,
                                    impUid: response.imp_uid,
                                    merchantUid: response.merchant_uid,
                                    amount: response.paid_amount
                                }
                            )

                            alert('결제가 완료되었습니다.')
                            await fetchBookings()
                        } catch (err) {
                            console.error('Payment confirmation failed:', err)
                            alert('결제 확인 중 오류가 발생했습니다.')
                        }
                    } else {
                        alert(`결제 실패: ${response.error_msg}`)
                    }
                })
            } catch (err) {
                console.error('Payment initiation failed:', err)
                alert('결제 시작 중 오류가 발생했습니다.')
            }
        }

        const fetchBookings = async () => {
            try {
                isLoading.value = true
                const response = await api.getUserBookings()
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
            formatPrice,
            getStatusText,
            getStatusClass,
            handlePayment
        }
    }
}
</script>