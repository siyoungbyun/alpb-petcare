<template>
  <nav class="fixed top-0 left-0 right-0 bg-white shadow-md z-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex justify-between h-20">
        <div class="flex items-center space-x-8">
          <router-link to="/" class="text-2xl font-bold text-gray-800">
            로고 여기
          </router-link>

          <div class="flex items-center space-x-6">
            <!-- User Services Dropdown -->
            <NavDropdown
              title="이용 서비스"
              :isActive="isActive"
              class="px-2"
            >
              <div class="space-y-1">
                <router-link
                  to="/pet-management"
                  class="text-gray-600 hover:bg-gray-50 hover:text-gray-900 group flex items-center px-3 py-2 text-sm font-medium rounded-md"
                  :class="{ 'bg-gray-100 text-gray-900': isActive('/pet-management') }"
                >
                  <span class="truncate">내 반려동물 관리</span>
                </router-link>
                <router-link
                  to="/pet-services"
                  class="text-gray-600 hover:bg-gray-50 hover:text-gray-900 group flex items-center px-3 py-2 text-sm font-medium rounded-md"
                  :class="{ 'bg-gray-100 text-gray-900': isActive('/pet-services') }"
                >
                  <span class="truncate">서비스 목록</span>
                </router-link>
                <router-link
                  to="/my-bookings"
                  class="text-gray-600 hover:bg-gray-50 hover:text-gray-900 group flex items-center px-3 py-2 text-sm font-medium rounded-md"
                  :class="{ 'bg-gray-100 text-gray-900': isActive('/my-bookings') }"
                >
                  <span class="truncate">내 예약 목록</span>
                </router-link>
              </div>
            </NavDropdown>

            <!-- Petsitter Services Dropdown -->
            <NavDropdown
              title="펫시터 서비스"
              :isActive="isActive"
              class="px-2"
            >
              <div class="space-y-1">
                <router-link
                  to="/pet-service-registration"
                  class="text-gray-600 hover:bg-gray-50 hover:text-gray-900 group flex items-center px-3 py-2 text-sm font-medium rounded-md"
                  :class="{ 'bg-gray-100 text-gray-900': isActive('/pet-service-registration') }"
                >
                  <span class="truncate">서비스 등록</span>
                </router-link>
                <router-link
                  :to="{ name: 'PetsitterProfile' }"
                  class="text-gray-600 hover:bg-gray-50 hover:text-gray-900 group flex items-center px-3 py-2 text-sm font-medium rounded-md"
                  :class="{ 'bg-gray-100 text-gray-900': isActive('/petsitter-profile') }"
                >
                  <span class="truncate">펫시터 프로필</span>
                </router-link>
                <router-link
                  to="/booking-management"
                  class="text-gray-600 hover:bg-gray-50 hover:text-gray-900 group flex items-center px-3 py-2 text-sm font-medium rounded-md"
                  :class="{ 'bg-gray-100 text-gray-900': isActive('/booking-management') }"
                >
                  <span class="truncate">예약 관리</span>
                </router-link>
              </div>
            </NavDropdown>

            <!-- Admin Dropdown -->
            <NavDropdown
              v-if="isAdmin"
              title="관리자"
              :isActive="isActive"
              class="px-2"
            >
              <div class="space-y-1">
                <router-link
                  to="/code-management"
                  class="text-gray-600 hover:bg-gray-50 hover:text-gray-900 group flex items-center px-3 py-2 text-sm font-medium rounded-md"
                  :class="{ 'bg-gray-100 text-gray-900': isActive('/code-management') }"
                >
                  <span class="truncate">코드 관리</span>
                </router-link>
              </div>
            </NavDropdown>
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
            <div class="relative" ref="menuContainer">
              <button
                @click="toggleMenu"
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
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import BaseButton from './BaseButton.vue'
import NavDropdown from './NavDropdown.vue'
import { api } from '../services/api'
import { useAuth } from '../store/auth'

export default {
  name: 'NavBar',
  components: {
    BaseButton,
    NavDropdown
  },
  setup() {
    const router = useRouter()
    const isMenuOpen = ref(false)
    const menuContainer = ref(null)
    const isAdmin = ref(false) // You'll need to implement admin check logic

    const isActive = (path) => {
      return router.currentRoute.value.path === path
    }

    const handleClickOutside = (event) => {
      if (menuContainer.value && !menuContainer.value.contains(event.target)) {
        isMenuOpen.value = false
      }
    }

    const toggleMenu = () => {
      isMenuOpen.value = !isMenuOpen.value
    }

    const handleLogout = async () => {
      try {
        await api.logout()
        useAuth.setLoggedIn(false)
        isMenuOpen.value = false
        router.push('/login')
      } catch (error) {
        console.error('Logout failed:', error)
      }
    }

    onMounted(() => {
      document.addEventListener('click', handleClickOutside)
    })

    onUnmounted(() => {
      document.removeEventListener('click', handleClickOutside)
    })

    return {
      isLoggedIn: useAuth.isLoggedIn,
      isAdmin,
      isMenuOpen,
      menuContainer,
      toggleMenu,
      handleLogout,
      isActive
    }
  }
}
</script>
