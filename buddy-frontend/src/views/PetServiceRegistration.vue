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

          <!-- Contact Number -->
          <div>
            <label class="block text-base font-medium text-gray-900 mb-2">
              연락처
            </label>
            <input
              v-model="form.contact"
              type="tel"
              class="block w-full px-4 py-3 rounded-lg border border-gray-300 bg-gray-100 shadow-sm focus:border-orange-500 focus:ring-1 focus:ring-orange-500 focus:outline-none"
              placeholder="010-XXXX-XXXX"
              required
            />
          </div>

          <!-- Service Type -->
          <div>
            <label class="block text-base font-medium text-gray-900 mb-2">
              서비스 선택
            </label>
            <div class="grid grid-cols-3 gap-4">
              <button
                type="button"
                @click="selectServiceType('산책')"
                :class="[
                  'py-3 px-4 rounded-lg border text-center',
                  form.serviceType === '산책' ? 'border-orange-500 bg-orange-50 text-orange-700' : 'border-gray-300 hover:border-gray-400'
                ]"
              >
                산책
              </button>
              <button
                type="button"
                @click="selectServiceType('돌봄')"
                :class="[
                  'py-3 px-4 rounded-lg border text-center',
                  form.serviceType === '돌봄' ? 'border-orange-500 bg-orange-50 text-orange-700' : 'border-gray-300 hover:border-gray-400'
                ]"
              >
                돌봄
              </button>
              <button
                type="button"
                @click="selectServiceType('미용')"
                :class="[
                  'py-3 px-4 rounded-lg border text-center',
                  form.serviceType === '미용' ? 'border-orange-500 bg-orange-50 text-orange-700' : 'border-gray-300 hover:border-gray-400'
                ]"
              >
                미용
              </button>
            </div>
          </div>

          <!-- Region -->
          <div>
            <label class="block text-base font-medium text-gray-900 mb-2">
              지역
            </label>
            <div class="flex flex-wrap gap-2">
              <button
                type="button"
                @click="toggleRegion('강북 (서울)')"
                :class="[
                  'px-4 py-2 rounded-lg border',
                  form.selectedRegions.includes('강북 (서울)') ? 'border-orange-500 bg-orange-50 text-orange-700' : 'border-gray-300 hover:border-gray-400'
                ]"
              >
                강북 (서울)
              </button>
              <button
                type="button"
                @click="toggleRegion('경기')"
                :class="[
                  'px-4 py-2 rounded-lg border',
                  form.selectedRegions.includes('경기') ? 'border-orange-500 bg-orange-50 text-orange-700' : 'border-gray-300 hover:border-gray-400'
                ]"
              >
                경기
              </button>
            </div>
          </div>

          <!-- Pet Types -->
          <div>
            <label class="block text-base font-medium text-gray-900 mb-2">
              돌봄 가능 반려동물 종류
            </label>
            <div class="flex flex-wrap gap-2">
              <button
                type="button"
                @click="togglePetType('골든 리트리버')"
                :class="[
                  'px-4 py-2 rounded-lg border',
                  form.petTypes.includes('골든 리트리버') ? 'border-orange-500 bg-orange-50 text-orange-700' : 'border-gray-300 hover:border-gray-400'
                ]"
              >
                골든 리트리버
              </button>
              <button
                type="button"
                @click="togglePetType('거북이')"
                :class="[
                  'px-4 py-2 rounded-lg border',
                  form.petTypes.includes('거북이') ? 'border-orange-500 bg-orange-50 text-orange-700' : 'border-gray-300 hover:border-gray-400'
                ]"
              >
                거북이
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
                  form.availableDaysOfWeek.includes(getDayOfWeek(day)) ? 'bg-orange-500 text-white' : 'border border-gray-300 text-gray-700 hover:border-gray-400'
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

export default {
  name: 'PetServiceRegistration',
  components: {
    BaseButton
  },
  data() {
    return {
      form: {
        name: '',
        contact: '',
        serviceType: '',
        selectedRegions: [],
        petTypes: [],
        price: '',
        description: '',
        availableDaysOfWeek: [],
        startTime: '',
        endTime: ''
      }
    }
  },
  methods: {
    selectServiceType(type) {
      this.form.serviceType = type
    },
    toggleRegion(region) {
      const index = this.form.selectedRegions.indexOf(region)
      if (index === -1) {
        this.form.selectedRegions.push(region)
      } else {
        this.form.selectedRegions.splice(index, 1)
      }
    },
    togglePetType(type) {
      const index = this.form.petTypes.indexOf(type)
      if (index === -1) {
        this.form.petTypes.push(type)
      } else {
        this.form.petTypes.splice(index, 1)
      }
    },
    toggleDay(day) {
      const dayOfWeek = this.getDayOfWeek(day)
      const index = this.form.availableDaysOfWeek.indexOf(dayOfWeek)
      if (index === -1) {
        this.form.availableDaysOfWeek.push(dayOfWeek)
      } else {
        this.form.availableDaysOfWeek.splice(index, 1)
      }
    },
    getDayOfWeek(day) {
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
    },
    async handleSubmit() {
      const serviceData = {
        name: this.form.name,
        serviceType: this.form.serviceType,
        availableLocation: this.form.selectedRegions.join(', '),
        description: this.form.description,
        price: Number(this.form.price),
        availableDaysOfWeek: this.form.availableDaysOfWeek,
        startTime: this.form.startTime,
        endTime: this.form.endTime
      }

      try {
        const response = await api.registerPetService(serviceData)
        console.log('Service registered successfully:', response)
        this.$router.push('/')
      } catch (error) {
        console.error('Error registering service:', error)
      }
    }
  }
}
</script>