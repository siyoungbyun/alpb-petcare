<template>
  <div class="min-h-screen bg-gray-50 pt-20">
    <div class="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <div class="bg-white rounded-xl shadow-md p-8 sm:p-12">
        <h1 class="text-2xl font-bold text-gray-900 mb-4 text-center">
          회원 탈퇴를 하시기 전에 확인해주세요!
        </h1>

        <!-- Terms -->
        <div class="bg-gray-50 rounded-lg p-6 mb-8">
          <p class="text-gray-600 text-base leading-relaxed whitespace-pre-line">
            회원 탈퇴 시 모든 개인정보와 이용기록이 삭제되며, 삭제된 데이터는 복구할 수 없습니다.

            • 등록된 반려동물 정보가 모두 삭제됩니다
            • 예약 내역 및 리뷰가 모두 삭제됩니다
            • 적립된 포인트가 모두 소멸됩니다
          </p>
        </div>

        <!-- Checkbox -->
        <div class="mb-8">
          <label class="flex items-center space-x-3 cursor-pointer">
            <input
              type="checkbox"
              v-model="agreed"
              class="w-5 h-5 text-orange-500 border-gray-300 rounded focus:ring-orange-500"
            >
            <span class="text-gray-700">
              주의사항을 읽었으며 동의합니다
            </span>
          </label>
          <p v-if="showValidationError" class="mt-2 text-sm text-red-600">
            회원 탈퇴를 진행하려면 주의사항에 동의해주세요
          </p>
        </div>

        <!-- Error Alert -->
        <div v-if="error" class="bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded-lg mb-6">
          {{ error }}
        </div>

        <!-- Buttons -->
        <div class="flex flex-col space-y-3">
          <BaseButton
            @click="handleWithdrawal"
            class="bg-red-500 hover:bg-red-600 text-white disabled:opacity-50 disabled:cursor-not-allowed"
            size="lg"
            block
            :disabled="!agreed || isLoading"
          >
            <template v-if="isLoading">
              처리 중...
            </template>
            <template v-else>
              회원 탈퇴
            </template>
          </BaseButton>

          <BaseButton
            @click="$router.push('/profile/edit')"
            variant="outline"
            size="lg"
            block
            class="border-gray-300 text-gray-700 hover:bg-gray-50"
            :disabled="isLoading"
          >
            취소
          </BaseButton>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BaseButton from '../components/BaseButton.vue'
import { scrollToTop } from '../mixins/scrollToTop'
import { api } from '../services/api'
import { useAuth } from '../store/auth'

export default {
  name: 'Withdrawal',
  components: {
    BaseButton
  },
  mixins: [scrollToTop],
  data() {
    return {
      agreed: false,
      isLoading: false,
      error: null,
      showValidationError: false
    }
  },
  methods: {
    async handleWithdrawal() {
      if (this.isLoading) return
      if (!this.agreed) {
        this.showValidationError = true
        return
      }

      this.showValidationError = false
      this.error = null
      this.isLoading = true

      try {
        await api.withdrawal()
        useAuth.setLoggedIn(false)
        this.$router.push('/')
      } catch (err) {
        this.error = err.message
      } finally {
        this.isLoading = false
      }
    }
  },
  watch: {
    agreed(newValue) {
      if (newValue) {
        this.showValidationError = false
      }
    }
  }
}
</script>