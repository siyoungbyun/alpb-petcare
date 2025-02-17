<template>
  <div class="relative group">
    <button
      class="flex items-center space-x-1 text-gray-700 hover:text-orange-500 transition-colors"
    >
      <span>{{ title }}</span>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        class="h-4 w-4 transform group-hover:rotate-180 transition-transform"
        fill="none"
        viewBox="0 0 24 24"
        stroke="currentColor"
      >
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
      </svg>
    </button>

    <!-- Dropdown menu -->
    <div
      class="absolute left-0 mt-2 w-48 bg-white rounded-lg shadow-lg py-2 invisible group-hover:visible opacity-0 group-hover:opacity-100 transition-all duration-200"
    >
      <slot :isActive="isActive"></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: 'NavDropdown',
  props: {
    title: {
      type: String,
      required: true
    },
    isActive: {
      type: Function,
      required: true
    }
  }
}
</script>