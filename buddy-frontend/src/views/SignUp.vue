<template>
  <div class="min-h-screen bg-gray-50 pt-20">
    <div class="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <div class="bg-white rounded-xl shadow-md p-8 sm:p-12">
        <h1 class="text-3xl font-bold text-gray-900 mb-12 text-center">
          회원가입
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

          <!-- Add confirm password field -->
          <div>
            <label for="confirmPassword" class="block text-sm font-medium text-gray-700">
              비밀번호 확인
            </label>
            <div class="mt-1">
              <input
                id="confirmPassword"
                v-model="confirmPassword"
                type="password"
                required
                class="block w-full px-4 py-3 rounded-lg border border-gray-300 shadow-sm focus:border-orange-500 focus:ring-1 focus:ring-orange-500 focus:outline-none text-base"
                placeholder="비밀번호를 입력해주세요"
                :class="{ 'border-red-300 focus:ring-red-500 focus:border-red-500': passwordMismatch }"
              />
            </div>
            <p v-if="passwordMismatch" class="mt-2 text-sm text-red-600">
              비밀번호가 일치하지 않습니다.
            </p>
          </div>


          <!-- Name Field -->
          <div class="space-y-2">
            <label class="block text-base font-medium text-gray-900">
              이름
            </label>
            <input
              v-model="form.name"
              type="text"
              class="block w-full px-4 py-3 rounded-lg border border-gray-300 shadow-sm focus:border-orange-500 focus:ring-1 focus:ring-orange-500 focus:outline-none text-base"
              placeholder="이름을 입력해주세요"
              required
              :disabled="isLoading"
            />
          </div>

          <!-- Phone Number Field -->
          <div class="space-y-2">
            <label class="block text-base font-medium text-gray-900">
              전화번호
            </label>
            <input
              v-model="form.phoneNumber"
              type="tel"
              class="block w-full px-4 py-3 rounded-lg border border-gray-300 shadow-sm focus:border-orange-500 focus:ring-1 focus:ring-orange-500 focus:outline-none text-base"
              placeholder="-"
              pattern="[0-9]{11}"
              required
              :disabled="isLoading"
            />
          </div>

          <!-- Terms Checkboxes -->
          <div class="space-y-4 py-4">
            <div class="flex items-center">
              <input
                v-model="form.termsService"
                type="checkbox"
                class="h-5 w-5 text-orange-500 focus:ring-orange-500 border-gray-300 rounded cursor-pointer"
                :disabled="isLoading"
              />
              <label class="ml-3 block text-base text-gray-900 cursor-pointer">
                [필수] 이용약관 동의
              </label>
            </div>

            <div class="flex items-center">
              <input
                v-model="form.termsPrivacy"
                type="checkbox"
                class="h-5 w-5 text-orange-500 focus:ring-orange-500 border-gray-300 rounded cursor-pointer"
                :disabled="isLoading"
              />
              <label class="ml-3 block text-base text-gray-900 cursor-pointer">
                [필수] 개인정보 수집 동의
              </label>
            </div>
          </div>

          <!-- Submit Button -->
          <BaseButton
            type="submit"
            variant="primary"
            size="lg"
            block
            class="mt-8"
            :disabled="isLoading || !isFormValid"
          >
            <template v-if="isLoading">
              가입 중...
            </template>
            <template v-else>
              회원가입하기
            </template>
          </BaseButton>
        </form>

        <div class="text-center mt-6">
          <p class="text-gray-600">
            이미 계정이 있으신가요?
            <button
              @click="goToLogin"
              class="text-orange-500 hover:text-orange-600 font-medium cursor-pointer"
              :disabled="isLoading"
            >
              로그인하기
            </button>
          </p>
        </div>
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
  name: 'SignUp',
  components: {
    BaseButton,
    Footer
  },
  mixins: [scrollToTop],
  data() {
    return {
      form: {
        email: '',
        password: '',
        name: '',
        phoneNumber: '',
        termsService: false,
        termsPrivacy: false
      },
      isLoading: false,
      error: null,
      confirmPassword: '',
      passwordMismatch: false
    }
  },
  computed: {
    isFormValid() {
      return (
        this.form.termsService &&
        this.form.termsPrivacy &&
        this.form.phoneNumber.length === 11 &&
        this.form.phoneNumber.match(/^[0-9]+$/) &&
        this.form.password === this.confirmPassword &&
        !this.passwordMismatch
      )
    }
  },
  methods: {
    async handleSubmit() {
      if (!this.isFormValid) {
        if (!this.form.termsService || !this.form.termsPrivacy) {
          this.error = '필수 약관에 모두 동의해주세요.'
        } else if (!this.form.phoneNumber.match(/^[0-9]{11}$/)) {
          this.error = '전화번호를 올바른 형식으로 입력해주세요.'
        } else if (this.form.password !== this.confirmPassword) {
          this.passwordMismatch = true
          this.error = '비밀번호가 일치하지 않습니다.'
        }
        return
      }

      this.error = null
      this.isLoading = true

      try {
        const signupData = {
          email: this.form.email,
          password: this.form.password,
          name: this.form.name,
          phoneNumber: this.form.phoneNumber
        }

        await api.signup(signupData)
        await api.login({
          email: this.form.email,
          password: this.form.password
        })
        useAuth.setLoggedIn(true)
        this.$router.push('/')
      } catch (err) {
        this.error = err.message
      } finally {
        this.isLoading = false
      }
    },
    goToLogin() {
      this.$router.push('/login')
    }
  }
}
</script>