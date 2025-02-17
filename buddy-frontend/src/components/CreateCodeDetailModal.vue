<template>
  <div v-if="show" class="fixed inset-0 z-50 overflow-y-auto">
    <div class="flex items-center justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
      <div class="fixed inset-0 transition-opacity" @click="$emit('close')">
        <div class="absolute inset-0 bg-gray-500 opacity-75"></div>
      </div>

      <div class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
        <form @submit.prevent="handleSubmit">
          <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
            <h3 class="text-lg font-medium leading-6 text-gray-900 mb-4">코드 상세 추가</h3>

            <div class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700">코드명</label>
                <input
                  v-model="form.name"
                  type="text"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-orange-500 focus:ring-orange-500"
                  required
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700">코드 상세 ID</label>
                <input
                  v-model="form.codeDetailId"
                  type="text"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-orange-500 focus:ring-orange-500"
                  required
                  pattern="[A-Za-z0-9_]+"
                />
                <p class="mt-1 text-sm text-gray-500">영문, 숫자, 언더스코어만 사용 가능</p>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700">코드 값</label>
                <input
                  v-model="form.value"
                  type="text"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-orange-500 focus:ring-orange-500"
                  required
                />
              </div>
            </div>
          </div>

          <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
            <button
              type="submit"
              class="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-orange-500 text-base font-medium text-white hover:bg-orange-600 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-orange-500 sm:ml-3 sm:w-auto sm:text-sm"
            >
              추가
            </button>
            <button
              type="button"
              @click="$emit('close')"
              class="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-orange-500 sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm"
            >
              취소
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  name: 'CreateCodeDetailModal',
  props: {
    show: Boolean
  },
  emits: ['close', 'submit'],
  setup(props, { emit }) {
    const form = ref({
      name: '',
      value: '',
      codeDetailId: ''
    })

    const handleSubmit = () => {
      emit('submit', { ...form.value })
      form.value = {
        name: '',
        value: '',
        codeDetailId: ''
      }
    }

    return {
      form,
      handleSubmit
    }
  }
}
</script>