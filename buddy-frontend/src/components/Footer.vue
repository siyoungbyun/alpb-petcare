<template>
  <footer class="bg-white border-t border-gray-200 mt-16">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Company Info Section -->
      <div class="grid grid-cols-2 gap-8 mb-8">
        <div>
          <h3 class="text-lg font-bold text-gray-900 mb-4">고객문의</h3>
          <div class="space-y-2">
            <div class="flex space-x-4">
              <button class="px-4 py-2 border border-gray-300 rounded-md hover:bg-gray-50">
                1:1 고객문의
              </button>
              <button class="px-4 py-2 border border-gray-300 rounded-md hover:bg-gray-50">
                제휴문의
              </button>
            </div>
            <p class="text-gray-500">
              평일 09:00-18:00 운영 / 주말, 공휴일 휴무
            </p>
          </div>
        </div>

        <div>
          <h3 class="text-lg font-bold text-gray-900 mb-4">법령생활</h3>
          <div class="flex space-x-4">
            <button class="px-4 py-2 border border-gray-300 rounded-md hover:bg-gray-50">
              속소 입점 문의
            </button>
            <button class="px-4 py-2 border border-gray-300 rounded-md hover:bg-gray-50">
              가게 입점 문의
            </button>
            <button class="px-4 py-2 border border-gray-300 rounded-md hover:bg-gray-50">
              마케팅/광고 제휴
            </button>
          </div>
        </div>
      </div>

      <!-- Contact Info -->
      <div class="text-sm text-gray-500 space-y-2">
        <p>고객센터: 02-2038-3701 (평일 오전 9시 ~ 오후 6시)</p>
        <p class="mt-4">
          통신판매중개자로서 통신판매의 당사자가 아니며 상품 거래정보 및 거래 등에 대해 책임을 지지 않습니다.
        </p>
        <div class="flex items-center space-x-4 mt-4">
          <span>이용약관</span>
          <span class="font-bold">전자금거래 이용약관</span>
          <span class="font-bold">개인정보 처리방침</span>
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'Footer'
}
</script>