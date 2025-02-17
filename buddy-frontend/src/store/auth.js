import { ref, computed } from 'vue'
import { tokenStorage } from '../utils/token'

const isLoggedIn = ref(!!tokenStorage.getToken())

export const useAuth = {
  isLoggedIn: computed(() => isLoggedIn.value),

  setLoggedIn(value) {
    isLoggedIn.value = value
    if (!value) {
      tokenStorage.removeToken()
    }
  }
}