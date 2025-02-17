export const tokenStorage = {
  getToken() {
    return localStorage.getItem('jwt_token')
  },

  setToken(token) {
    localStorage.setItem('jwt_token', token)
  },

  removeToken() {
    localStorage.removeItem('jwt_token')
  }
}