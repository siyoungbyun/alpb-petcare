<template>
  <div class="min-h-screen bg-gray-50 pt-20">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <h1 class="text-2xl font-bold text-gray-900 mb-8">펫시터 목록</h1>

      <!-- Loading State -->
      <div v-if="isLoading" class="text-center py-12">
        <p class="text-gray-500">로딩 중...</p>
      </div>

      <!-- Error State -->
      <div v-else-if="error" class="bg-red-50 border border-red-200 text-red-600 px-4 py-3 rounded-lg">
        {{ error }}
      </div>

      <!-- Petsitter List -->
      <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <div v-for="petsitter in petsitters"
             :key="petsitter.id"
             class="bg-white rounded-lg shadow-sm p-6 border border-gray-200">
          <div class="space-y-4">
            <div class="flex items-center space-x-4">
              <div
                class="w-12 h-12 rounded-full flex items-center justify-center text-white text-lg font-medium"
                :style="{ backgroundColor: getRandomColor(petsitter.name) }"
              >
                {{ getInitial(petsitter.name) }}
              </div>
              <h3 class="text-lg font-medium text-gray-900">
                {{ petsitter.name }}
              </h3>
            </div>

            <div class="flex items-center text-gray-500">
              <svg class="h-5 w-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z" />
              </svg>
              <span>{{ formatPhoneNumber(petsitter.phoneNumber) }}</span>
            </div>

            <div class="pt-2 border-t border-gray-100">
              <p class="text-gray-600 whitespace-pre-line">
                {{ petsitter.description || '소개가 없습니다.' }}
              </p>
            </div>
          </div>
        </div>

        <!-- Empty State -->
        <div v-if="petsitters.length === 0" class="col-span-full text-center py-12 bg-white rounded-lg">
          <p class="text-gray-500">등록된 펫시터가 없습니다.</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { api } from '../services/api'

export default {
  name: 'PetsitterList',
  setup() {
    const petsitters = ref([])
    const isLoading = ref(true)
    const error = ref(null)

    const formatPhoneNumber = (phone) => {
      if (!phone) return '';
      return phone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
    }

    const getInitial = (name) => {
      return name ? name.charAt(0).toUpperCase() : '?';
    }

    const getRandomColor = (seed) => {
      // List of pleasant, muted colors
      const colors = [
        '#4F46E5', // Indigo
        '#7C3AED', // Purple
        '#EC4899', // Pink
        '#EF4444', // Red
        '#F59E0B', // Amber
        '#10B981', // Emerald
        '#3B82F6', // Blue
        '#8B5CF6', // Violet
        '#6366F1', // Indigo
        '#14B8A6'  // Teal
      ];

      // Use the name as a seed to get a consistent color for each petsitter
      const index = seed.split('').reduce((acc, char) => acc + char.charCodeAt(0), 0);
      return colors[index % colors.length];
    }

    const fetchPetsitters = async () => {
      try {
        isLoading.value = true
        const response = await api.getPetsitters()
        petsitters.value = response
      } catch (err) {
        console.error('Failed to fetch petsitters:', err)
        error.value = err.message || '펫시터 목록을 불러오는데 실패했습니다.'
      } finally {
        isLoading.value = false
      }
    }

    onMounted(fetchPetsitters)

    return {
      petsitters,
      isLoading,
      error,
      formatPhoneNumber,
      getInitial,
      getRandomColor
    }
  }
}
</script>