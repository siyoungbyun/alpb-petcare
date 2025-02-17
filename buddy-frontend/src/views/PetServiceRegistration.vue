<template>
  <div class="min-h-screen bg-gray-50 pt-20">
    <div class="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <div class="bg-white rounded-xl shadow-md p-8 sm:p-12">
        <h1 class="text-3xl font-bold text-gray-900 mb-8 text-center">
          펫시터 서비스 등록
        </h1>

        <form @submit.prevent="handleSubmit" class="space-y-6">
          <!-- Service Name -->
          <div>
            <label class="block text-base font-medium text-gray-900 mb-2">
              서비스 이름
            </label>
            <input
              v-model="form.name"
              type="text"
              class="block w-full px-4 py-3 rounded-lg border border-gray-300 shadow-sm focus:border-orange-500 focus:ring-1 focus:ring-orange-500 focus:outline-none"
              placeholder="서비스 이름을 입력해주세요."
              required
            />
          </div>

          <!-- Service Type -->
          <div>
            <label class="block text-base font-medium text-gray-900 mb-2">
              서비스 선택
            </label>
            <div class="flex flex-wrap gap-2">
              <button
                v-for="serviceType in serviceTypes"
                :key="serviceType.id"
                @click.prevent="selectServiceType(serviceType)"
                class="px-4 py-2 rounded-lg border"
                :class="[
                  form.serviceType?.id === serviceType.id
                    ? 'border-orange-500 bg-orange-50 text-orange-700'
                    : 'border-gray-200 hover:border-gray-300 text-gray-700'
                ]"
              >
                {{ serviceType.name }}
              </button>
            </div>
          </div>

          <!-- Region -->
          <div>
            <label class="block text-base font-medium text-gray-900 mb-2">
              지역
            </label>
            <div class="overflow-x-auto scrollbar-hide">
              <div class="flex gap-2 pb-2">
                <button
                  v-for="area in areas"
                  :key="area.id"
                  @click.prevent="toggleArea(area)"
                  class="px-4 py-2 rounded-lg border"
                  :class="[
                    form.locations.some(a => a.id === area.id)
                      ? 'border-orange-500 bg-orange-50 text-orange-700'
                      : 'border-gray-200 hover:border-gray-300 text-gray-700'
                  ]"
                >
                  {{ area.name }}
                </button>
              </div>
            </div>
          </div>

          <!-- Pet Types -->
          <div>
            <label class="block text-base font-medium text-gray-900 mb-2">
              돌봄 가능 반려동물 종류
            </label>
            <div class="flex flex-wrap gap-2">
              <button
                v-for="petType in petTypes"
                :key="petType.id"
                @click.prevent="togglePetType(petType)"
                :class="[
                  'px-4 py-2 rounded-lg border',
                  form.animalTypes.some(p => p.id === petType.id)
                    ? 'border-orange-500 bg-orange-50 text-orange-700'
                    : 'border-gray-200 hover:border-gray-300 text-gray-700'
                ]"
              >
                {{ petType.name }}
              </button>
            </div>
          </div>

          <!-- Price -->
          <div>
            <label class="block text-base font-medium text-gray-900 mb-2">
              요금 (원 / 시간)
            </label>
            <input
              v-model="form.price"
              type="number"
              class="block w-full px-4 py-3 rounded-lg border border-gray-300 shadow-sm focus:border-orange-500 focus:ring-1 focus:ring-orange-500 focus:outline-none"
              placeholder="15,000"
              required
            />
          </div>

          <!-- Description -->
          <div>
            <label class="block text-base font-medium text-gray-900 mb-2">
              서비스 소개
            </label>
            <textarea
              v-model="form.description"
              class="block w-full px-4 py-3 rounded-lg border border-gray-300 shadow-sm focus:border-orange-500 focus:ring-1 focus:ring-orange-500 focus:outline-none"
              rows="4"
              placeholder="서비스에 대한 설명을 입력해주세요"
              required
            ></textarea>
          </div>

          <!-- Available Days -->
          <div>
            <label class="block text-base font-medium text-gray-900 mb-2">
              돌봄 가능 요일 선택
            </label>
            <div class="flex space-x-2">
              <button
                v-for="day in ['월', '화', '수', '목', '금', '토', '일']"
                :key="day"
                type="button"
                @click="toggleDay(day)"
                :class="[
                  'w-10 h-10 rounded-full flex items-center justify-center',
                  form.availableDays.includes(getDayOfWeek(day)) ? 'bg-orange-500 text-white' : 'border border-gray-300 text-gray-700 hover:border-gray-400'
                ]"
              >
                {{ day }}
              </button>
            </div>
          </div>

          <!-- Available Time -->
          <div>
            <label class="block text-base font-medium text-gray-900 mb-2">
              돌봄 가능 시간 선택
            </label>
            <div class="flex items-center space-x-4">
              <input
                v-model="form.startTime"
                type="time"
                class="block w-32 px-4 py-3 rounded-lg border border-gray-300 shadow-sm focus:border-orange-500 focus:ring-1 focus:ring-orange-500 focus:outline-none"
                required
              />
              <span class="text-gray-500">-</span>
              <input
                v-model="form.endTime"
                type="time"
                class="block w-32 px-4 py-3 rounded-lg border border-gray-300 shadow-sm focus:border-orange-500 focus:ring-1 focus:ring-orange-500 focus:outline-none"
                required
              />
            </div>
          </div>

          <!-- All Day Toggle -->
          <div>
            <label class="flex items-center space-x-2 cursor-pointer">
              <input
                type="checkbox"
                v-model="form.allDay"
                class="form-checkbox h-5 w-5 text-orange-500 rounded focus:ring-orange-500"
              />
              <span class="text-base font-medium text-gray-900">24시간 서비스</span>
            </label>
          </div>

          <!-- Submit Button -->
          <BaseButton type="submit" variant="primary" size="lg" block class="mt-8">
            서비스 등록하기
          </BaseButton>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import BaseButton from '../components/BaseButton.vue'
import { api } from '../services/api'
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

export default {
  name: 'PetServiceRegistration',
  components: {
    BaseButton
  },
  setup() {
    const router = useRouter()
    const form = ref({
      name: '',
      description: '',
      price: '',
      serviceType: null,
      startTime: '',
      endTime: '',
      availableDays: [],
      locations: [],
      animalTypes: [],
      allDay: false
    })
    const areas = ref([])
    const petTypes = ref([])
    const serviceTypes = ref([])
    const isLoading = ref(false)
    const error = ref(null)

    const fetchServiceTypes = async () => {
      try {
        const response = await api.getCodeDetails(2)
        serviceTypes.value = response
      } catch (err) {
        console.error('Failed to fetch service types:', err)
        error.value = '서비스 종류 목록을 불러오는데 실패했습니다.'
      }
    }

    const fetchPetTypes = async () => {
      try {
        const response = await api.getCodeDetails(1) // 2 is the code group ID for pet types
        petTypes.value = response
      } catch (err) {
        console.error('Failed to fetch pet types:', err)
        error.value = '반려동물 종류 목록을 불러오는데 실패했습니다.'
      }
    }

    const fetchAreas = async () => {
      try {
        const response = await api.getCodeDetails(3) // 3 is the code group ID for areas
        areas.value = response
      } catch (err) {
        console.error('Failed to fetch areas:', err)
        error.value = '지역 목록을 불러오는데 실패했습니다.'
      }
    }

    const toggleArea = (area) => {
      const index = form.value.locations.findIndex(a => a.id === area.id)
      if (index === -1) {
        form.value.locations.push(area)
      } else {
        form.value.locations.splice(index, 1)
      }
    }

    const handleSubmit = async () => {
      const formatTimeToISO = (timeString) => {
        if (!timeString) return null
        // Create a new Date object for today
        const today = new Date()
        // Split the time string into hours and minutes
        const [hours, minutes] = timeString.split(':')
        // Set the hours and minutes on today's date
        today.setHours(hours, minutes, 0, 0)
        // Return ISO string
        return today.toISOString()
      }

      const serviceData = {
        ...form.value,
        price: Number(form.value.price),
        serviceType: form.value.serviceType?.name,
        locations: form.value.locations.map(loc => loc.name),
        animalTypes: form.value.animalTypes.map(type => type.name),
        startTime: formatTimeToISO(form.value.startTime),
        endTime: formatTimeToISO(form.value.endTime)
      }

      try {
        const response = await api.registerPetService(serviceData)
        console.log('Service registered successfully:', response)
        router.push('/petsitter-profile')
      } catch (error) {
        console.error('Error registering service:', error)
        alert('서비스 등록에 실패했습니다.')
      }
    }

    const selectServiceType = (serviceType) => {
      form.value.serviceType = serviceType
    }

    const togglePetType = (petType) => {
      const index = form.value.animalTypes.findIndex(p => p.id === petType.id)
      if (index === -1) {
        form.value.animalTypes.push(petType)
      } else {
        form.value.animalTypes.splice(index, 1)
      }
    }

    const toggleDay = (day) => {
      const dayOfWeek = getDayOfWeek(day)
      const index = form.value.availableDays.indexOf(dayOfWeek)
      if (index === -1) {
        form.value.availableDays.push(dayOfWeek)
      } else {
        form.value.availableDays.splice(index, 1)
      }
    }

    const getDayOfWeek = (day) => {
      const dayMap = {
        '월': 'MONDAY',
        '화': 'TUESDAY',
        '수': 'WEDNESDAY',
        '목': 'THURSDAY',
        '금': 'FRIDAY',
        '토': 'SATURDAY',
        '일': 'SUNDAY'
      }
      return dayMap[day]
    }

    onMounted(() => {
      fetchAreas()
      fetchPetTypes()
      fetchServiceTypes()
    })

    return {
      form,
      isLoading,
      error,
      areas,
      petTypes,
      serviceTypes,
      toggleArea,
      handleSubmit,
      selectServiceType,
      togglePetType,
      toggleDay,
      getDayOfWeek
    }
  }
}
</script>

<style scoped>
.scrollbar-hide {
  -ms-overflow-style: none;  /* IE and Edge */
  scrollbar-width: none;     /* Firefox */
}

.scrollbar-hide::-webkit-scrollbar {
  display: none;  /* Chrome, Safari and Opera */
}
</style>