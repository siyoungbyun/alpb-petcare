const API_BASE_URL = 'http://localhost:8080/api/v1'

export const api = {
  async login(credentials) {
    const response = await fetch(`${API_BASE_URL}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(credentials),
      credentials: 'include' // This is important for session cookies
    })

    const data = await response.json()

    if (!response.ok) {
      throw new Error(data.message || '로그인에 실패했습니다.')
    }

    return data
  },

  async registerPetService(data) {
    const response = await fetch(`${API_BASE_URL}/pet-services`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
      credentials: 'include'
    });

    const responseData = await response.json();

    if (!response.ok) {
      throw new Error(responseData.message || '서비스 등록에 실패했습니다.');
    }

    return responseData;
  },

  async checkAuth() {
    const response = await fetch(`${API_BASE_URL}/me`, {
      credentials: 'include'
    })
    return response.ok
  },

  async logout() {
    const response = await fetch(`${API_BASE_URL}/auth/logout`, {
      method: 'POST',
      credentials: 'include'
    })
    return response.ok
  },

  async getMyProfile() {
    const response = await fetch(`${API_BASE_URL}/me`, {
      credentials: 'include'
    })

    const data = await response.json()

    if (!response.ok) {
      throw new Error(data.message || '프로필 조회에 실패했습니다.')
    }

    return data.data
  },

  async updateProfile(data) {
    const response = await fetch(`${API_BASE_URL}/me`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
      credentials: 'include'
    })

    const responseData = await response.json()

    if (!response.ok) {
      throw new Error(responseData.message || '프로필 수정에 실패했습니다.')
    }

    return responseData.data
  },

  async withdrawal() {
    const response = await fetch(`${API_BASE_URL}/me`, {
      method: 'DELETE',
      credentials: 'include'
    })

    if (!response.ok) {
      const data = await response.json()
      throw new Error(data.message || '회원 탈퇴에 실패했습니다.')
    }

    return true
  },

  async signup(data) {
    const response = await fetch(`${API_BASE_URL}/auth/signup`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
      credentials: 'include'
    })

    const responseData = await response.json()

    if (!response.ok) {
      throw new Error(responseData.message || '회원가입에 실패했습니다.')
    }

    return responseData
  },

  async getPetServices() {
    const response = await fetch(`${API_BASE_URL}/pet-services`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
      credentials: 'include'
    });

    const responseData = await response.json();

    if (!response.ok) {
      throw new Error(responseData.message || '서비스 목록 조회에 실패했습니다.');
    }

    return responseData.data;
  }
}