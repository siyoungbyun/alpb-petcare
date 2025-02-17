<template>
  <div>
    <div class="min-h-screen bg-gray-50 pt-20">
      <div class="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <!-- Profile Header -->
        <div class="bg-white rounded-xl shadow-md p-8 mb-8">
          <div class="flex items-center space-x-8">
            <div class="w-24 h-24 rounded-full bg-orange-500 flex items-center justify-center text-white text-3xl font-medium">
              P
            </div>
            <div>
              <h1 class="text-2xl font-bold text-gray-900 mb-2">펫시터 프로필</h1>
              <p class="text-gray-600">펫시터 서비스 관리</p>
            </div>
          </div>
        </div>

        <!-- Loading State -->
        <div v-if="isLoading" class="py-8 text-center text-gray-500">
          로딩 중...
        </div>

        <!-- Error State -->
        <div v-else-if="error" class="py-8 text-center text-red-500">
          {{ error }}
        </div>

        <!-- No Profile State -->
        <div v-else-if="!profile" class="py-8">
          <div class="bg-white rounded-xl shadow-md p-8">
            <div class="text-center">
              <div class="mb-6">
                <div class="w-20 h-20 mx-auto bg-orange-100 rounded-full flex items-center justify-center">
                  <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 text-orange-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
                  </svg>
                </div>
              </div>
              <h3 class="text-xl font-medium text-gray-900 mb-2">펫시터 프로필 만들기</h3>
              <p class="text-gray-500 mb-6">
                펫시터 프로필을 만들어 반려동물 돌봄 서비스를 시작해보세요.
              </p>
              <button
                @click="showCreateModal = true"
                class="px-6 py-3 bg-orange-500 text-white hover:bg-orange-600 rounded-lg transition-colors text-base font-medium cursor-pointer"
              >
                프로필 생성하기
              </button>
            </div>
          </div>
        </div>

        <!-- Profile Display -->
        <div v-else>
          <div class="space-y-10">
            <!-- Basic Information Card -->
            <div class="bg-white rounded-xl shadow-md p-8">
              <h2 class="text-2xl font-bold text-gray-700 mb-6">기본 정보</h2>
              <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
                <div>
                  <p class="text-sm text-gray-500">이름</p>
                  <p class="text-gray-800 mt-2">{{ userProfile?.name || '-' }}</p>
                </div>
                <div>
                  <p class="text-sm text-gray-500">연락처</p>
                  <p class="text-gray-800 mt-2">{{ userProfile?.phoneNumber || '-' }}</p>
                </div>
                <div>
                  <p class="text-sm text-gray-500">소개</p>
                  <p class="text-gray-800 mt-2">{{ profile.introduction }}</p>
                </div>
                <div>
                  <p class="text-sm text-gray-500">경력</p>
                  <p class="text-gray-800 mt-2">{{ profile.experience }} 년</p>
                </div>
              </div>
            </div>

            <!-- Services Information Card -->
            <div class="bg-white rounded-xl shadow-md p-8">
              <h2 class="text-2xl font-bold text-gray-700 mb-6">서비스 정보</h2>
              <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
                <div>
                  <p class="text-sm text-gray-500">가능한 서비스</p>
                  <ul class="mt-2 list-disc list-inside text-gray-800">
                    <li v-for="serviceType in availableServiceTypes" :key="serviceType">
                      {{ serviceType }}
                    </li>
                  </ul>
                </div>
              </div>
            </div>

            <!-- Petsitter Services Cards -->
            <div class="bg-white rounded-xl shadow-md p-8">
              <div class="flex justify-between items-center mb-6">
                <h2 class="text-2xl font-bold text-gray-700">등록된 서비스</h2>
                <router-link
                  to="/pet-service-registration"
                  class="text-orange-500 hover:text-orange-600 font-medium cursor-pointer"
                >
                  서비스 추가
                </router-link>
              </div>

              <div class="overflow-x-auto">
                <div class="flex space-x-6 pb-4">
                  <div
                    v-for="service in services"
                    :key="service.id"
                    class="flex-none w-80 bg-gray-50 rounded-lg p-6 border border-gray-100 hover:border-orange-200 transition-colors cursor-pointer"
                  >
                    <h3 class="font-medium text-lg text-gray-900 mb-2">{{ service.name }}</h3>
                    <div class="space-y-2 text-sm">
                      <p class="text-gray-600">
                        <span class="text-gray-500">서비스 종류:</span> {{ service.serviceType }}
                      </p>
                      <p class="text-gray-600">
                        <span class="text-gray-500">가격:</span> {{ service.price.toLocaleString() }}원
                      </p>
                      <p class="text-gray-600">
                        <span class="text-gray-500">가능 요일:</span> {{ formatDays(service.availableDays) }}
                      </p>
                      <p class="text-gray-600">
                        <span class="text-gray-500">가능 시간:</span>
                        {{ service.allDay ? '24시간' : `${formatTime(service.startTime)} - ${formatTime(service.endTime)}` }}
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Action Buttons -->
            <div class="flex justify-end space-x-6">
              <button
                @click="showUpdateModal = true"
                class="px-6 py-3 bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200 transition-colors text-base font-medium cursor-pointer"
              >
                수정
              </button>
              <button
                @click="handleDelete"
                class="px-6 py-3 bg-red-100 text-red-600 rounded-lg hover:bg-red-200 transition-colors text-base font-medium cursor-pointer"
              >
                삭제
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Create Profile Modal -->
      <PetsitterProfileModal
        v-if="showCreateModal"
        @close="showCreateModal = false"
        @submit="handleCreate"
      />
    </div>
  </div>
</template>

<script>
import { ref, onMounted, computed } from 'vue'
import { api } from '../services/api'
import PetsitterProfileModal from '../components/PetsitterProfileModal.vue'

export default {
  name: 'PetsitterProfile',
  components: {
    PetsitterProfileModal
  },
  setup() {
    const profile = ref(null)
    const userProfile = ref(null)
    const isLoading = ref(true)
    const error = ref(null)
    const showCreateModal = ref(false)
    const showUpdateModal = ref(false)
    const services = ref([])

    // Compute unique service types from actual services
    const availableServiceTypes = computed(() => {
      const types = services.value.map(service => service.serviceType)
      return [...new Set(types)] // Remove duplicates
    })

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

    const fetchServices = async () => {
      try {
        const response = await api.getPetServices()
        services.value = response
      } catch (err) {
        console.error('Failed to fetch services:', err)
      }
    }

    const fetchProfile = async () => {
      try {
        isLoading.value = true
        // Fetch both petsitter profile and user profile in parallel
        const [petsitterResponse, userProfileResponse] = await Promise.all([
          api.getPetsitterProfile(),
          api.getMyProfile()
        ])

        // Set user profile data
        userProfile.value = userProfileResponse

        // Handle petsitter profile
        if (petsitterResponse.status === 'SUCCESS') {
          profile.value = {
            introduction: petsitterResponse.data.description,
            experience: 0,
            services: []
          }
          fetchServices() // Fetch services after confirming petsitter profile exists
        } else if (petsitterResponse.status === 'ERROR' && petsitterResponse.message === '프로필이 없습니다.') {
          profile.value = null
        } else {
          throw new Error(petsitterResponse.message || '프로필을 불러오는데 실패했습니다.')
        }
      } catch (err) {
        error.value = err.message || '프로필을 불러오는데 실패했습니다.'
        console.error('Failed to fetch profile:', err)
      } finally {
        isLoading.value = false
      }
    }

    const handleDelete = async () => {
      if (!confirm('정말 프로필을 삭제하시겠습니까?')) return

      try {
        await api.deletePetsitterProfile()
        profile.value = null
      } catch (err) {
        alert(err.message || '프로필 삭제에 실패했습니다.')
        console.error('Failed to delete profile:', err)
      }
    }

    const handleCreate = async (data) => {
      try {
        const response = await api.createPetsitterProfile(data)
        if (response.status === 'SUCCESS') {
          profile.value = {
            introduction: response.data.description,
            experience: 0,
            services: []
          }
        }
      } catch (err) {
        alert(err.message || '프로필 생성에 실패했습니다.')
        console.error('Failed to create profile:', err)
      }
    }

    onMounted(() => {
      fetchProfile()
    })

    return {
      profile,
      userProfile,
      isLoading,
      error,
      showCreateModal,
      showUpdateModal,
      handleDelete,
      handleCreate,
      services,
      formatDays,
      formatTime,
      availableServiceTypes
    }
  }
}
</script>

<style scoped>
/* Your styles here */
/* Optional: Add custom styles to enhance the profile's appearance */

.bg-white {
  background-color: #ffffff;
}

.bg-gray-50 {
  background-color: #f9fafb;
}

.bg-gray-100 {
  background-color: #f3f4f6;
}

.bg-blue-600 {
  background-color: #3182ce;
}

.bg-red-600 {
  background-color: #e53e3e;
}

.text-gray-800 {
  color: #2d3748;
}

.text-gray-700 {
  color: #4a5568;
}

.text-gray-500 {
  color: #a0aec0;
}

.text-gray-600 {
  color: #718096;
}

.text-sm {
  font-size: 0.875rem;
}

.text-xl {
  font-size: 1.25rem;
}

.text-2xl {
  font-size: 1.5rem;
}

.text-3xl {
  font-size: 1.875rem;
}

.font-semibold {
  font-weight: 600;
}

.font-bold {
  font-weight: 700;
}

.rounded-lg {
  border-radius: 0.5rem;
}

.rounded-xl {
  border-radius: 0.75rem;
}

.rounded-2xl {
  border-radius: 1rem;
}

.shadow-md {
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.shadow-lg {
  box-shadow: 0 10px 15px rgba(0, 0, 0, 0.1);
}

.p-6 {
  padding: 1.5rem;
}

.p-8 {
  padding: 2rem;
}

.p-10 {
  padding: 2.5rem;
}

.p-12 {
  padding: 3rem;
}

.mb-4 {
  margin-bottom: 1rem;
}

.mb-6 {
  margin-bottom: 1.5rem;
}

.mb-8 {
  margin-bottom: 2rem;
}

.mt-1 {
  margin-top: 0.25rem;
}

.space-y-8 > :not([hidden]) ~ :not([hidden]) {
  margin-top: 2rem;
}

.space-y-10 > :not([hidden]) ~ :not([hidden]) {
  margin-top: 2.5rem;
}

.space-y-4 > :not([hidden]) ~ :not([hidden]) {
  margin-top: 1rem;
}

.space-y-6 > :not([hidden]) ~ :not([hidden]) {
  margin-top: 1.5rem;
}

.grid {
  display: grid;
}

.grid-cols-1 {
  grid-template-columns: repeat(1, minmax(0, 1fr));
}

.md\:grid-cols-2 {
  @media (min-width: 768px) {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

.gap-6 {
  grid-gap: 1.5rem;
}

.gap-8 {
  grid-gap: 2rem;
}

.text-center {
  text-align: center;
}

.hover\:bg-orange-600:hover {
  background-color: #dd6b20;
}

.hover\:bg-blue-700:hover {
  background-color: #2b6cb0;
}

.hover\:bg-red-700:hover {
  background-color: #c53030;
}

.hover\:bg-gray-700:hover {
  background-color: #4a5568;
}

.transition-colors {
  transition: background-color 0.3s, color 0.3s;
}

.rounded-lg {
  border-radius: 0.5rem;
}

.rounded-xl {
  border-radius: 0.75rem;
}

.rounded-2xl {
  border-radius: 1rem;
}

.cursor-pointer {
  cursor: pointer;
}

.hover\:bg-gray-50:hover {
  background-color: #f9fafb;
}

.shadow-md {
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.shadow-lg {
  box-shadow: 0 10px 15px rgba(0, 0, 0, 0.1);
}

/* Additional Custom Styles */
.bg-gray-100 {
  background-color: #f3f4f6;
}

.text-gray-800 {
  color: #2d3748;
}

.text-gray-700 {
  color: #4a5568;
}

.text-gray-800 {
  color: #2d3748;
}

.text-gray-500 {
  color: #a0aec0;
}

.text-gray-600 {
  color: #718096;
}

.font-bold {
  font-weight: 700;
}

.font-semibold {
  font-weight: 600;
}

.text-xl {
  font-size: 1.25rem;
}

.text-2xl {
  font-size: 1.5rem;
}

.text-3xl {
  font-size: 1.875rem;
}

.bg-orange-600 {
  background-color: #ed8936;
}

.bg-blue-600 {
  background-color: #4299e1;
}

.bg-red-600 {
  background-color: #f56565;
}

.hover\:bg-orange-700:hover {
  background-color: #c05621;
}

.hover\:bg-blue-700:hover {
  background-color: #2b6cb0;
}

.hover\:bg-red-700:hover {
  background-color: #c53030;
}
</style>