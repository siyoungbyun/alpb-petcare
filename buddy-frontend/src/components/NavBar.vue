<template>
  <nav class="fixed top-0 left-0 right-0 bg-white shadow-md z-50">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex justify-between h-20">
        <div class="flex items-center space-x-8">
          <router-link to="/" class="text-2xl font-bold text-gray-800">
            BUDDY
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
                  to="/petsitters"
                  class="text-gray-600 hover:bg-gray-50 hover:text-gray-900 group flex items-center px-3 py-2 text-sm font-medium rounded-md"
                  :class="{ 'bg-gray-100 text-gray-900': isActive('/petsitters') }"
                >
                  <span class="truncate">펫시터 찾기</span>
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

            <!-- Admin Dropdown - now visible for all logged in users -->
            <NavDropdown
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

        <!-- Auth buttons section -->
        <div class="flex items-center">
          <template v-if="isLoggedIn">
            <BaseButton @click="handleLogout" variant="secondary">
              로그아웃
            </BaseButton>
          </template>
          <template v-else>
            <BaseButton @click="$router.push('/login')" variant="secondary" class="mr-4">
              로그인
            </BaseButton>
            <BaseButton @click="$router.push('/signup')" variant="primary">
              회원가입
            </BaseButton>
          </template>
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
      isMenuOpen,
      menuContainer,
      toggleMenu,
      handleLogout,
      isActive
    }
  }
}
</script>
