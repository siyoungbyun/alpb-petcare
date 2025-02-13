<template>
  <div class="min-h-screen bg-gray-50 pt-20">
    <div class="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <div class="bg-white rounded-xl shadow-md p-8 sm:p-12">
        <h1 class="text-2xl font-bold text-gray-900 mb-8 text-center">
          프로필 수정
        </h1>

        <form @submit.prevent="handleSubmit" class="space-y-8">
          <!-- Error Alert -->
          <div v-if="error" class="bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded-lg">
            {{ error }}
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

          <!-- Submit Button -->
          <BaseButton
            type="submit"
            variant="primary"
            size="lg"
            block
            :disabled="isLoading"
          >
            <template v-if="isLoading">
              저장 중...
            </template>
            <template v-else>
              저장하기
            </template>
          </BaseButton>
        </form>

        <!-- Divider -->
        <div class="mt-12 mb-8 border-t border-gray-200"></div>

        <!-- Withdrawal Section -->
        <div class="text-center">
          <router-link
            to="/withdrawal"
            class="text-red-600 hover:text-red-700 text-sm font-medium"
          >
            회원 탈퇴하기
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BaseButton from '../components/BaseButton.vue'
import { scrollToTop } from '../mixins/scrollToTop'
import { api } from '../services/api'

export default {
  name: 'UpdateProfile',
  components: {
    BaseButton
  },
  mixins: [scrollToTop],
  data() {
    return {
      form: {
        name: ''
      },
      isLoading: false,
      error: null
    }
  },
  async created() {
    try {
      const profileData = await api.getMyProfile()
      this.form.name = profileData.name
    } catch (err) {
      this.error = err.message
      console.error('Failed to fetch profile:', err)
    }
  },
  methods: {
    async handleSubmit() {
      this.error = null
      this.isLoading = true

      try {
        await api.updateProfile(this.form)
        this.$router.push('/profile')
      } catch (err) {
        this.error = err.message
      } finally {
        this.isLoading = false
      }
    }
  }
}
</script>