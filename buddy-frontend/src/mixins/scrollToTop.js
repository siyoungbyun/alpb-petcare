export const scrollToTop = {
  mounted() {
    window.scrollTo({
      top: 0,
      behavior: 'smooth'
    })
  }
}