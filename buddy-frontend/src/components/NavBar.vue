<template>
  <nav class="fixed top-0 left-0 right-0 bg-white shadow-md z-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex justify-between h-20">
        <div class="flex items-center space-x-8">
          <router-link to="/" class="text-2xl font-bold text-gray-800">
            로고 여기
          </router-link>

          <div class="flex items-center space-x-1">
            <router-link
              to="/pet-management"
              class="px-4 py-2 text-gray-700 hover:bg-gray-100 rounded-md transition-colors text-base"
              :class="{ 'bg-gray-100': $route.path === '/pet-management' }"
            >
              내 반려동물 관리
            </router-link>
            <router-link
              to="/service-booking"
              class="px-4 py-2 text-gray-700 hover:bg-gray-100 rounded-md transition-colors text-base"
              :class="{ 'bg-gray-100': $route.path === '/service-booking' }"
            >
              펫시터 서비스 예약
            </router-link>
          </div>
        </div>

        <!-- Auth buttons -->
        <div class="flex items-center">
          <div v-if="!isLoggedIn" class="flex space-x-2">
            <BaseButton
              v-if="$route.path !== '/login'"
              to="/login"
              variant="outline"
            >
              로그인
            </BaseButton>
            <BaseButton
              v-if="$route.path !== '/signup'"
              to="/signup"
              variant="primary"
            >
              회원가입
            </BaseButton>
          </div>
          <div v-else class="flex items-center space-x-4">
            <!-- Profile Circle - Make it a router link -->
            <router-link
              to="/profile"
              class="w-10 h-10 rounded-full bg-orange-500 flex items-center justify-center text-white font-medium hover:bg-orange-600 transition-colors"
            >
              P
            </router-link>
            <!-- Menu Button with Dropdown Container -->
            <div class="relative">
              <button
                @click="isMenuOpen = !isMenuOpen"
                class="p-2 hover:bg-gray-100 rounded-lg transition-colors cursor-pointer"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke-width="1.5"
                  stroke="currentColor"
                  class="w-6 h-6 text-gray-600"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5"
                  />
                </svg>
              </button>

              <!-- Dropdown Menu -->
              <div
                v-if="isMenuOpen"
                class="absolute right-0 top-full mt-2 w-48 bg-white rounded-lg shadow-lg py-1 border border-gray-200 z-50"
              >
                <button
                  @click="handleLogout"
                  class="w-full px-4 py-2 text-left text-gray-700 hover:bg-gray-100 transition-colors cursor-pointer"
                >
                  로그아웃
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<script>
import BaseButton from './BaseButton.vue'
import { api } from '../services/api'
import { useAuth } from '../store/auth'
import { ref, onMounted, onBeforeUnmount, watch } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'NavBar',
  components: {
    BaseButton
  },
  setup() {
    const router = useRouter()
    const isMenuOpen = ref(false)

    const handleLogout = async () => {
      try {
        await api.logout()
        useAuth.setLoggedIn(false)
        isMenuOpen.value = false
        if (router.currentRoute.value.path !== '/') {
          router.push('/')
        }
      } catch (error) {
        console.error('Logout failed:', error)
      }
    }

    const handleClickOutside = (event) => {
      const dropdown = document.querySelector('.relative')
      if (dropdown && !dropdown.contains(event.target)) {
        isMenuOpen.value = false
      }
    }

    onMounted(async () => {
      const isAuthenticated = await api.checkAuth()
      useAuth.setLoggedIn(isAuthenticated)
      document.addEventListener('click', handleClickOutside)
    })

    onBeforeUnmount(() => {
      document.removeEventListener('click', handleClickOutside)
    })

    return {
      isMenuOpen,
      isLoggedIn: useAuth.isLoggedIn,
      handleLogout,
      handleClickOutside
    }
  }
}
</script>
