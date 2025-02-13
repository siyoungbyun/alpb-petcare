<template>
  <div class="min-h-screen bg-gray-50 pt-20">
    <div class="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <div class="bg-white rounded-xl shadow-md p-8 sm:p-12">
        <h1 class="text-3xl font-bold text-gray-900 mb-12 text-center">
          로그인
        </h1>

        <form @submit.prevent="handleSubmit" class="space-y-8">
          <!-- Error Alert -->
          <div v-if="error" class="bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded-lg">
            {{ error }}
          </div>

          <!-- Email Field -->
          <div class="space-y-2">
            <label class="block text-base font-medium text-gray-900">
              이메일
            </label>
            <input
              v-model="form.email"
              type="email"
              class="block w-full px-4 py-3 rounded-lg border border-gray-300 shadow-sm focus:border-orange-500 focus:ring-1 focus:ring-orange-500 focus:outline-none text-base"
              placeholder="example@email.com"
              required
              :disabled="isLoading"
            />
          </div>

          <!-- Password Field -->
          <div class="space-y-2">
            <label class="block text-base font-medium text-gray-900">
              비밀번호
            </label>
            <input
              v-model="form.password"
              type="password"
              class="block w-full px-4 py-3 rounded-lg border border-gray-300 shadow-sm focus:border-orange-500 focus:ring-1 focus:ring-orange-500 focus:outline-none text-base"
              placeholder="비밀번호를 입력해주세요"
              required
              :disabled="isLoading"
            />
          </div>

          <!-- Login Button -->
          <BaseButton
            type="submit"
            variant="primary"
            size="lg"
            block
            class="mt-8"
            :disabled="isLoading"
          >
            <template v-if="isLoading">
              로그인 중...
            </template>
            <template v-else>
              로그인
            </template>
          </BaseButton>

          <!-- Sign Up Link -->
          <div class="text-center mt-6">
            <p class="text-gray-600">
              아직 회원이 아니신가요?
              <button
                @click="goToSignup"
                class="text-orange-500 hover:text-orange-600 font-medium cursor-pointer"
                :disabled="isLoading"
              >
                회원가입하기
              </button>
            </p>
          </div>
        </form>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script>
import BaseButton from '../components/BaseButton.vue'
import Footer from '../components/Footer.vue'
import { scrollToTop } from '../mixins/scrollToTop'
import { api } from '../services/api'
import { useAuth } from '../store/auth'

export default {
  name: 'Login',
  components: {
    BaseButton,
    Footer
  },
  mixins: [scrollToTop],
  data() {
    return {
      form: {
        email: '',
        password: ''
      },
      isLoading: false,
      error: null
    }
  },
  methods: {
    async handleSubmit() {
      this.error = null
      this.isLoading = true

      try {
        await api.login(this.form)
        useAuth.setLoggedIn(true)  // Set logged in state immediately after successful login
        this.$router.push('/')
      } catch (err) {
        this.error = err.message
      } finally {
        this.isLoading = false
      }
    },
    goToSignup() {
      this.$router.push('/signup')
    }
  }
}
</script>