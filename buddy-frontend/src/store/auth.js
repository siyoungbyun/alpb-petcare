import { ref, computed } from 'vue'

const isLoggedIn = ref(false)

export const useAuth = {
  isLoggedIn: computed(() => isLoggedIn.value),

  setLoggedIn(value) {
    isLoggedIn.value = value
  }
}