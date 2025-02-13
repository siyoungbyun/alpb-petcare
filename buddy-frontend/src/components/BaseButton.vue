<template>
  <component
    :is="to ? 'router-link' : 'button'"
    :to="to"
    :type="!to ? type : undefined"
    :class="[
      'px-4 py-3 rounded-lg font-medium transition-colors focus:outline-none inline-flex items-center justify-center cursor-pointer',
      variant === 'primary' ? 'bg-orange-500 hover:bg-orange-600 text-white' : '',
      variant === 'outline' ? 'border border-gray-800 hover:bg-gray-100 text-gray-800' : '',
      block ? 'w-full' : '',
      size === 'lg' ? 'text-lg py-4' : 'text-sm',
      className
    ]"
    @click="$emit('click')"
  >
    <slot></slot>
  </component>
</template>

<script>
export default {
  name: 'BaseButton',
  props: {
    type: {
      type: String,
      default: 'button'
    },
    variant: {
      type: String,
      default: 'primary',
      validator: value => ['primary', 'outline'].includes(value)
    },
    size: {
      type: String,
      default: 'default',
      validator: value => ['default', 'lg'].includes(value)
    },
    block: {
      type: Boolean,
      default: false
    },
    className: {
      type: String,
      default: ''
    },
    to: {
      type: [String, Object],
      default: null
    }
  }
}
</script>