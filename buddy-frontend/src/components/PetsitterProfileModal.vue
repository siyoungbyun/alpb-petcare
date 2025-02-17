<template>
  <div class="fixed inset-0 bg-gray-500 bg-opacity-75 flex items-center justify-center z-50">
    <div class="bg-white rounded-lg max-w-md w-full p-6">
      <div class="flex justify-between items-center mb-6">
        <h3 class="text-lg font-medium text-gray-900">
          펫시터 프로필 {{ isEdit ? '수정' : '생성' }}
        </h3>
        <button
          @click="$emit('close')"
          class="text-gray-400 hover:text-gray-500 cursor-pointer"
        >
          <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
          </svg>
        </button>
      </div>

      <form @submit.prevent="handleSubmit">
        <div class="space-y-4">
          <div>
            <label for="description" class="block text-sm font-medium text-gray-700 mb-1">
              소개
            </label>
            <textarea
              id="description"
              v-model="form.description"
              rows="4"
              class="block w-full rounded-md border-gray-300 shadow-sm focus:border-orange-500 focus:ring-orange-500"
              :disabled="isLoading"
              required
            ></textarea>
          </div>
        </div>

        <div class="mt-6 flex justify-end space-x-3">
          <button
            type="button"
            @click="$emit('close')"
            class="px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-orange-500 cursor-pointer"
            :disabled="isLoading"
          >
            취소
          </button>
          <button
            type="submit"
            class="px-4 py-2 text-sm font-medium text-white bg-orange-600 border border-transparent rounded-md hover:bg-orange-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-orange-500 cursor-pointer"
            :disabled="isLoading"
          >
            {{ isLoading ? '처리 중...' : (isEdit ? '수정' : '생성') }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  name: 'PetsitterProfileModal',
  props: {
    isEdit: {
      type: Boolean,
      default: false
    },
    initialData: {
      type: Object,
      default: () => ({})
    }
  },
  emits: ['close', 'submit'],
  setup(props, { emit }) {
    const isLoading = ref(false)
    const form = ref({
      description: props.initialData.description || ''
    })

    const handleSubmit = async () => {
      isLoading.value = true
      try {
        await emit('submit', form.value)
        emit('close')
      } catch (error) {
        console.error('Failed to submit:', error)
      } finally {
        isLoading.value = false
      }
    }

    return {
      form,
      isLoading,
      handleSubmit
    }
  }
}
</script>