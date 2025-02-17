import { tokenStorage } from '../utils/token'

const API_BASE_URL = 'http://localhost:8080/api/v1'

const getHeaders = () => {
  const headers = {
    'Content-Type': 'application/json'
  }

  const token = tokenStorage.getToken()
  if (token) {
    headers['Authorization'] = `Bearer ${token}`
  }

  return headers
}

export const api = {
  async login(credentials) {
    const response = await fetch(`${API_BASE_URL}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(credentials)
    })

    const data = await response.json()

    if (!response.ok) {
      throw new Error(data.message || '로그인에 실패했습니다.')
    }

    // Store the JWT token
    tokenStorage.setToken(data.data.token)
    return data
  },

  async checkAuth() {
    const token = tokenStorage.getToken()
    if (!token) return false

    try {
      const response = await fetch(`${API_BASE_URL}/me`, {
        headers: getHeaders()
      })
      return response.ok
    } catch (error) {
      return false
    }
  },

  async logout() {
    // For JWT, we just need to remove the token
    tokenStorage.removeToken()
    return true
  },

  async getMyProfile() {
    const response = await fetch(`${API_BASE_URL}/me`, {
      headers: getHeaders()
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
      headers: getHeaders(),
      body: JSON.stringify(data)
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
      headers: getHeaders()
    })

    if (!response.ok) {
      const data = await response.json()
      throw new Error(data.message || '회원 탈퇴에 실패했습니다.')
    }

    tokenStorage.removeToken()
    return true
  },

  async signup(data) {
    const response = await fetch(`${API_BASE_URL}/auth/signup`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data)
    })

    const responseData = await response.json()

    if (!response.ok) {
      throw new Error(responseData.message || '회원가입에 실패했습니다.')
    }

    return responseData
  },

  async registerPetService(data) {
    const response = await fetch(`${API_BASE_URL}/pet-services`, {
      method: 'POST',
      headers: getHeaders(),
      body: JSON.stringify(data)
    })

    const responseData = await response.json()

    if (!response.ok) {
      throw new Error(responseData.message || '서비스 등록에 실패했습니다.')
    }

    return responseData
  },

  async getPetServices() {
    const response = await fetch(`${API_BASE_URL}/pet-services`, {
      headers: getHeaders()
    })

    const responseData = await response.json()

    if (!response.ok) {
      throw new Error(responseData.message || '서비스 목록 조회에 실패했습니다.')
    }

    return responseData.data
  },

  async getCodeGroups() {
    const response = await fetch(`${API_BASE_URL}/code-groups`, {
      headers: getHeaders()
    })

    if (!response.ok) {
      const responseData = await response.json()
      throw new Error(responseData.message || '코드 그룹 목록 조회에 실패했습니다.')
    }

    const responseData = await response.json()
    return responseData.data
  },

  async getCodeDetails(codeGroupId) {
    const response = await fetch(`${API_BASE_URL}/code-groups/${codeGroupId}/code-details`, {
      headers: getHeaders()
    })

    if (!response.ok) {
      const responseData = await response.json()
      throw new Error(responseData.message || '코드 상세 목록 조회에 실패했습니다.')
    }

    const responseData = await response.json()
    return responseData.data
  },

  async deleteCodeGroup(codeGroupId) {
    const response = await fetch(`${API_BASE_URL}/code-groups/${codeGroupId}`, {
      method: 'DELETE',
      headers: getHeaders()
    })

    if (!response.ok) {
      const responseData = await response.json()
      throw new Error(responseData.message || '코드 그룹 삭제에 실패했습니다.')
    }

    return true
  },

  async deleteCodeDetail(codeDetailId) {
    const response = await fetch(`${API_BASE_URL}/code-details/${codeDetailId}`, {
      method: 'DELETE',
      headers: getHeaders()
    })

    if (!response.ok) {
      const responseData = await response.json()
      throw new Error(responseData.message || '코드 상세 삭제에 실패했습니다.')
    }

    return true
  },

  async createCodeGroup(data) {
    const response = await fetch(`${API_BASE_URL}/code-groups`, {
      method: 'POST',
      headers: getHeaders(),
      body: JSON.stringify(data)
    })

    if (!response.ok) {
      const responseData = await response.json()
      throw new Error(responseData.message || '코드 그룹 생성에 실패했습니다.')
    }

    const responseData = await response.json()
    return responseData.data
  },

  async createCodeDetail(codeGroupId, data) {
    const response = await fetch(`${API_BASE_URL}/code-groups/${codeGroupId}/code-details`, {
      method: 'POST',
      headers: getHeaders(),
      body: JSON.stringify(data)
    })

    if (!response.ok) {
      const responseData = await response.json()
      throw new Error(responseData.message || '코드 상세 생성에 실패했습니다.')
    }

    const responseData = await response.json()
    return responseData.data
  },

  async updateCodeGroup(codeGroupId, data) {
    const response = await fetch(`${API_BASE_URL}/code-groups/${codeGroupId}`, {
      method: 'PATCH',
      headers: getHeaders(),
      body: JSON.stringify(data)
    })

    if (!response.ok) {
      const responseData = await response.json()
      throw new Error(responseData.message || '코드 그룹 수정에 실패했습니다.')
    }

    const responseData = await response.json()
    return responseData.data
  },

  async updateCodeDetail(codeDetailId, data) {
    const response = await fetch(`${API_BASE_URL}/code-details/${codeDetailId}`, {
      method: 'PATCH',
      headers: getHeaders(),
      body: JSON.stringify(data)
    })

    if (!response.ok) {
      const responseData = await response.json()
      throw new Error(responseData.message || '코드 상세 수정에 실패했습니다.')
    }

    const responseData = await response.json()
    return responseData.data
  },

  async getPetsitterProfile() {
    const response = await fetch(`${API_BASE_URL}/me/petsitter-profile`, {
      headers: getHeaders()
    })

    const responseData = await response.json()

    if (!response.ok && !(responseData.status === 'ERROR' && responseData.message === '프로필이 없습니다.')) {
      throw new Error(responseData.message || '프로필 조회에 실패했습니다.')
    }

    return responseData
  },

  async createPetsitterProfile(data) {
    const response = await fetch(`${API_BASE_URL}/me/petsitter-profile`, {
      method: 'POST',
      headers: getHeaders(),
      body: JSON.stringify(data)
    })

    const responseData = await response.json()

    if (!response.ok) {
      throw new Error(responseData.message || '프로필 생성에 실패했습니다.')
    }

    return responseData
  },

  async deletePetsitterProfile() {
    const response = await fetch(`${API_BASE_URL}/me/petsitter-profile`, {
      method: 'DELETE',
      headers: getHeaders()
    })

    if (!response.ok) {
      const responseData = await response.json()
      throw new Error(responseData.message || '프로필 삭제에 실패했습니다.')
    }

    return true
  }
}