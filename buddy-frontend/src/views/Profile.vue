<template>
  <div class="min-h-screen bg-gray-50 pt-20">
    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <!-- Profile Header -->
      <div class="bg-white rounded-xl shadow-md p-8 mb-8">
        <div class="flex items-center space-x-8">
          <div class="w-24 h-24 rounded-full bg-orange-500 flex items-center justify-center text-white text-3xl font-medium">
            P
          </div>
          <div>
            <h1 class="text-2xl font-bold text-gray-900 mb-2">{{ userData.name }}</h1>
            <p class="text-gray-600">{{ userData.email }}</p>
          </div>
        </div>
      </div>

      <!-- Profile Sections -->
      <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
        <!-- Personal Info -->
        <div class="bg-white rounded-xl shadow-md p-8">
          <h2 class="text-xl font-bold text-gray-900 mb-6">자기소개</h2>
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-600 mb-1">
                이름
              </label>
              <p class="text-gray-900">{{ userData.name }}</p>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-600 mb-1">
                이메일
              </label>
              <p class="text-gray-900">{{ userData.email }}</p>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-600 mb-1">
                연락처
              </label>
              <p class="text-gray-900">{{ userData.phone }}</p>
            </div>
            <router-link
              to="/profile/edit"
              class="mt-4 w-full px-4 py-2 bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200 transition-colors inline-block text-center"
            >
              프로필 수정
            </router-link>
          </div>
        </div>

        <!-- Pet List -->
        <div class="bg-white rounded-xl shadow-md p-8">
          <div class="flex justify-between items-center mb-6">
            <h2 class="text-xl font-bold text-gray-900">내 반려동물</h2>
            <button class="text-orange-500 hover:text-orange-600 font-medium">
              반려동물 추가
            </button>
          </div>
          <div class="space-y-4">
            <div class="flex items-center justify-between p-4 border rounded-lg hover:border-orange-500 transition-colors cursor-pointer">
              <div class="flex items-center space-x-4">
                <div class="w-12 h-12 bg-orange-100 rounded-full flex items-center justify-center">
                  <span class="text-orange-500">멍</span>
                </div>
                <div>
                  <p class="font-medium text-gray-900">멍멍이 1</p>
                  <p class="text-sm text-gray-500">강아지 • 2살</p>
                </div>
              </div>
              <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-gray-400" viewBox="0 0 20 20" fill="currentColor">
                <path fill-rule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd" />
              </svg>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { scrollToTop } from '../mixins/scrollToTop'
import { api } from '../services/api'

export default {
  name: 'Profile',
  mixins: [scrollToTop],
  data() {
    return {
      userData: {
        id: null,
        name: '',
        email: '',
        phone: '아직 등록되지 않음'  // Default value since it's not available from API
      },
      isLoading: true,
      error: null
    }
  },
  async created() {
    try {
      const profileData = await api.getMyProfile()
      this.userData = {
        ...this.userData,
        ...profileData
      }
    } catch (err) {
      this.error = err.message
      console.error('Failed to fetch profile:', err)
    } finally {
      this.isLoading = false
    }
  }
}
</script>