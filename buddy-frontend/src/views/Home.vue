<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Hero Banner Section -->
    <div class="relative h-[500px] pt-16">
      <!-- Background Image -->
      <div
        class="absolute inset-0 w-full h-full bg-cover bg-center bg-no-repeat"
        style="background-image: url('/src/assets/dog-banner.jpg');"
      >
        <!-- Overlay for better text readability -->
        <div class="absolute inset-0 bg-black bg-opacity-40"></div>
      </div>

      <!-- Content -->
      <div class="relative h-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center h-full">
          <div class="text-white">
            <h1 class="text-4xl md:text-5xl lg:text-6xl font-bold leading-tight">
              믿을 만한 이웃 간의<br/>
              반려동물 서비스
            </h1>
            <p class="mt-4 text-xl text-gray-200">
              이웃과 함께하는 반려동물 돌봄 서비스를 경험해보세요
            </p>
          </div>
        </div>
      </div>
    </div>

    <!-- Popular Services Section -->
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <h2 class="text-2xl font-bold text-gray-900 mb-8">
        인기 펫시터 서비스
      </h2>

      <!-- Scrollable Container with Navigation -->
      <div class="relative">
        <!-- Previous Button -->
        <button
          @click="scrollCards('left')"
          class="absolute left-0 top-1/2 -translate-y-1/2 -translate-x-4 z-10 bg-white rounded-full p-2 shadow-md hover:bg-gray-50 focus:outline-none cursor-pointer"
        >
          <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-gray-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
          </svg>
        </button>

        <!-- Cards Container -->
        <div
          ref="cardsContainer"
          class="overflow-x-hidden scroll-smooth"
        >
          <div class="flex space-x-4 py-2">
            <!-- Service Cards -->
            <div
              v-for="(service, index) in services"
              :key="index"
              class="flex-none w-64 bg-white rounded-lg shadow-sm border border-gray-100 overflow-hidden transition-transform duration-300 hover:scale-105 cursor-pointer"
            >
              <div :class="`aspect-square ${service.bgColor}`">
                <div class="w-full h-full flex items-center justify-center">
                  <span :class="`${service.textColor} text-xl font-medium`">{{ service.name }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Next Button -->
        <button
          @click="scrollCards('right')"
          class="absolute right-0 top-1/2 -translate-y-1/2 translate-x-4 z-10 bg-white rounded-full p-2 shadow-md hover:bg-gray-50 focus:outline-none cursor-pointer"
        >
          <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-gray-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
          </svg>
        </button>
      </div>
    </div>

    <Footer />
  </div>
</template>

<script>
import Footer from '../components/Footer.vue'

export default {
  name: 'Home',
  components: {
    Footer
  },
  data() {
    return {
      services: [
        { name: '산책', bgColor: 'bg-red-50', textColor: 'text-red-300' },
        { name: '놀아주기', bgColor: 'bg-green-50', textColor: 'text-green-300' },
        { name: '밥 주기', bgColor: 'bg-blue-50', textColor: 'text-blue-300' },
        { name: '??', bgColor: 'bg-yellow-50', textColor: 'text-yellow-300' },
        { name: '미용', bgColor: 'bg-gray-50', textColor: 'text-gray-300' },
        // Add more services if needed
        { name: '서비스 6', bgColor: 'bg-purple-50', textColor: 'text-purple-300' },
        { name: '서비스 7', bgColor: 'bg-pink-50', textColor: 'text-pink-300' },
        { name: '서비스 8', bgColor: 'bg-indigo-50', textColor: 'text-indigo-300' },
      ]
    }
  },
  methods: {
    scrollCards(direction) {
      const container = this.$refs.cardsContainer;
      const scrollAmount = 200; // Adjust this value to control scroll distance

      if (direction === 'left') {
        container.scrollBy({
          left: -scrollAmount,
          behavior: 'smooth'
        });
      } else {
        container.scrollBy({
          left: scrollAmount,
          behavior: 'smooth'
        });
      }
    }
  }
}
</script>