<template>
  <div class="min-h-screen bg-gray-50 pt-20">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <div class="grid grid-cols-2 gap-8">
        <!-- Common Code Section (Left) -->
        <div>
          <div class="bg-white rounded-lg shadow-sm p-6">
            <div class="flex justify-between items-center mb-6">
              <h2 class="text-xl font-bold text-gray-900">공통코드</h2>
              <button
                @click="handleAddCommonCode"
                class="px-4 py-2 bg-orange-500 text-white hover:bg-orange-600 rounded-lg transition-colors"
              >
                추가
              </button>
            </div>

            <!-- Loading State -->
            <div v-if="isLoading" class="py-8 text-center text-gray-500">
              로딩 중...
            </div>

            <!-- Error State -->
            <div v-else-if="error" class="py-8 text-center text-red-500">
              {{ error }}
            </div>

            <!-- Data Table -->
            <table v-else class="min-w-full divide-y divide-gray-200">
              <thead class="bg-gray-50">
                <tr>
                  <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    코드 그룹 ID
                  </th>
                  <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                    코드 그룹명
                  </th>
                  <th scope="col" class="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                    관리
                  </th>
                </tr>
              </thead>
              <tbody class="bg-white divide-y divide-gray-200">
                <tr
                  v-for="code in commonCodes"
                  :key="code.id"
                  class="hover:bg-gray-50 cursor-pointer"
                  @click="selectCommonCode(code)"
                  :class="{ 'bg-orange-50': selectedGroupId === code.id }"
                >
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    {{ code.codeGroupId }}
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                    {{ code.name }}
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900 text-center">
                    <div class="flex items-center justify-center space-x-2">
                      <button
                        @click.stop="handleEditCommonCode(code)"
                        class="text-blue-600 hover:text-blue-800"
                      >
                        수정
                      </button>
                      <button
                        @click.stop="handleDeleteCommonCode(code.id)"
                        class="text-red-600 hover:text-red-800"
                      >
                        삭제
                      </button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- Specific Code Section (Right) -->
        <div>
          <div class="bg-white rounded-lg shadow-sm p-6">
            <h2 class="text-xl font-bold text-gray-900 mb-6">공통코드상세</h2>

            <!-- No Selection State -->
            <div v-if="!selectedGroupId" class="py-8 text-center text-gray-500">
              좌측에서 코드 그룹을 선택해주세요
            </div>

            <template v-else>
              <!-- Selected Code Group Info -->
              <div class="mb-8 bg-gray-50 rounded-lg p-4">
                <div class="grid grid-cols-2 gap-4">
                  <div>
                    <p class="text-sm text-gray-500 mb-1">코드 그룹 ID</p>
                    <p class="font-medium">{{ selectedGroup.codeGroupId }}</p>
                  </div>
                  <div>
                    <p class="text-sm text-gray-500 mb-1">코드 그룹명</p>
                    <p class="font-medium">{{ selectedGroup.name }}</p>
                  </div>
                  <div class="col-span-2">
                    <p class="text-sm text-gray-500 mb-1">설명</p>
                    <p class="font-medium">{{ selectedGroup.description }}</p>
                  </div>
                  <div>
                    <p class="text-sm text-gray-500 mb-1">생성일</p>
                    <p class="font-medium">{{ formatDate(selectedGroup.createdAt) }}</p>
                  </div>
                  <div>
                    <p class="text-sm text-gray-500 mb-1">수정일</p>
                    <p class="font-medium">{{ formatDate(selectedGroup.updatedAt) }}</p>
                  </div>
                </div>
              </div>

              <!-- Code Details Controls -->
              <div class="flex justify-end mb-6">
                <button
                  @click="handleAddSpecificCode"
                  class="px-4 py-2 bg-orange-500 text-white hover:bg-orange-600 rounded-lg transition-colors"
                >
                  추가
                </button>
              </div>

              <!-- Loading State -->
              <div v-if="isLoadingDetails" class="py-8 text-center text-gray-500">
                로딩 중...
              </div>

              <!-- Error State -->
              <div v-else-if="detailsError" class="py-8 text-center text-red-500">
                {{ detailsError }}
              </div>

              <!-- Data Table -->
              <table v-else class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-50">
                  <tr>
                    <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      코드 그룹 ID
                    </th>
                    <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      자식코드
                    </th>
                    <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      코드명
                    </th>
                    <th scope="col" class="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">
                      관리
                    </th>
                  </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                  <tr v-for="code in specificCodes" :key="code.id" class="hover:bg-gray-50">
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {{ selectedGroup.codeGroupId }}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {{ code.childCode }}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                      {{ code.name }}
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900 text-center">
                      <div class="flex items-center justify-center space-x-2">
                        <button
                          @click="handleEditSpecificCode(code)"
                          class="text-blue-600 hover:text-blue-800"
                        >
                          수정
                        </button>
                        <button
                          @click="handleDeleteSpecificCode(code.id)"
                          class="text-red-600 hover:text-red-800"
                        >
                          삭제
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </template>
          </div>
        </div>
      </div>
    </div>
  </div>

  <CreateCodeGroupModal
    :show="showCreateGroupModal"
    @close="showCreateGroupModal = false"
    @submit="handleCreateCodeGroup"
  />

  <CreateCodeDetailModal
    :show="showCreateDetailModal"
    @close="showCreateDetailModal = false"
    @submit="handleCreateCodeDetail"
  />

  <UpdateCodeGroupModal
    :show="showUpdateGroupModal"
    :code-group="selectedGroupForEdit"
    @close="showUpdateGroupModal = false"
    @submit="handleUpdateCodeGroup"
  />

  <UpdateCodeDetailModal
    :show="showUpdateDetailModal"
    :code-detail="selectedDetailForEdit"
    @close="showUpdateDetailModal = false"
    @submit="handleUpdateCodeDetail"
  />
</template>

<script>
import { ref, onMounted } from 'vue'
import { api } from '../services/api'
import CreateCodeGroupModal from '../components/CreateCodeGroupModal.vue'
import CreateCodeDetailModal from '../components/CreateCodeDetailModal.vue'
import UpdateCodeGroupModal from '../components/UpdateCodeGroupModal.vue'
import UpdateCodeDetailModal from '../components/UpdateCodeDetailModal.vue'

export default {
  name: 'CodeManagement',
  components: {
    CreateCodeGroupModal,
    CreateCodeDetailModal,
    UpdateCodeGroupModal,
    UpdateCodeDetailModal
  },
  setup() {
    const commonCodes = ref([])
    const specificCodes = ref([])
    const isLoading = ref(true)
    const error = ref(null)
    const selectedGroupId = ref(null)
    const isLoadingDetails = ref(false)
    const detailsError = ref(null)
    const showCreateGroupModal = ref(false)
    const showCreateDetailModal = ref(false)
    const selectedGroup = ref(null)
    const showUpdateGroupModal = ref(false)
    const showUpdateDetailModal = ref(false)
    const selectedGroupForEdit = ref(null)
    const selectedDetailForEdit = ref(null)

    const formatDate = (dateString) => {
      if (!dateString) return '-'
      const date = new Date(dateString)
      return date.toLocaleDateString('ko-KR', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
      })
    }

    const fetchCodeGroups = async () => {
      try {
        const codeGroups = await api.getCodeGroups()
        commonCodes.value = codeGroups
      } catch (err) {
        error.value = err.message
        console.error('Failed to fetch code groups:', err)
      } finally {
        isLoading.value = false
      }
    }

    onMounted(fetchCodeGroups)

    const selectCommonCode = async (code) => {
      selectedGroupId.value = code.id
      isLoadingDetails.value = true
      detailsError.value = null

      try {
        const group = commonCodes.value.find(g => g.id === code.id)
        selectedGroup.value = group

        const details = await api.getCodeDetails(code.id)
        specificCodes.value = details.map(detail => ({
          id: detail.id,
          parentCode: code.parentCode,
          childCode: detail.codeDetailId,
          name: detail.name
        }))
      } catch (err) {
        detailsError.value = err.message
        console.error('Failed to fetch code details:', err)
      } finally {
        isLoadingDetails.value = false
      }
    }

    const handleAddCommonCode = () => {
      showCreateGroupModal.value = true
    }

    const handleCreateCodeGroup = async (data) => {
      try {
        const newCodeGroup = await api.createCodeGroup(data)
        commonCodes.value.push({
          id: newCodeGroup.id,
          parentCode: newCodeGroup.codeGroupId,
          name: newCodeGroup.name
        })
        showCreateGroupModal.value = false
      } catch (error) {
        alert(error.message)
        console.error('Failed to create code group:', error)
      }
    }

    const handleDeleteCommonCode = async (id) => {
      if (confirm('정말 삭제하시겠습니까?')) {
        try {
          await api.deleteCodeGroup(id)
          commonCodes.value = commonCodes.value.filter(code => code.id !== id)
          if (selectedGroupId.value === id) {
            selectedGroupId.value = null
            specificCodes.value = []
          }
        } catch (error) {
          alert(error.message)
          console.error('Failed to delete code group:', error)
        }
      }
    }

    const handleAddSpecificCode = () => {
      if (!selectedGroupId.value) {
        alert('코드 그룹을 먼저 선택해주세요')
        return
      }
      showCreateDetailModal.value = true
    }

    const handleCreateCodeDetail = async (data) => {
      try {
        const newCodeDetail = await api.createCodeDetail(selectedGroupId.value, data)
        specificCodes.value.push({
          id: newCodeDetail.id,
          parentCode: commonCodes.value.find(code => code.id === selectedGroupId.value)?.parentCode,
          childCode: newCodeDetail.codeDetailId,
          name: newCodeDetail.name
        })
        showCreateDetailModal.value = false
      } catch (error) {
        alert(error.message)
        console.error('Failed to create code detail:', error)
      }
    }

    const handleEditCommonCode = (code) => {
      selectedGroupForEdit.value = code
      showUpdateGroupModal.value = true
    }

    const handleEditSpecificCode = (code) => {
      selectedDetailForEdit.value = code
      showUpdateDetailModal.value = true
    }

    const handleDeleteSpecificCode = async (id) => {
      if (confirm('정말 삭제하시겠습니까?')) {
        try {
          await api.deleteCodeDetail(id)
          specificCodes.value = specificCodes.value.filter(code => code.id !== id)
        } catch (error) {
          alert(error.message)
          console.error('Failed to delete code detail:', error)
        }
      }
    }

    const handleUpdateCodeGroup = async (data) => {
      try {
        const updatedGroup = await api.updateCodeGroup(selectedGroupForEdit.value.id, data)
        const index = commonCodes.value.findIndex(code => code.id === updatedGroup.id)
        if (index !== -1) {
          commonCodes.value[index] = updatedGroup
        }
        showUpdateGroupModal.value = false

        // Update selected group if it was the one being edited
        if (selectedGroupId.value === updatedGroup.id) {
          selectedGroup.value = updatedGroup
        }
      } catch (error) {
        alert(error.message)
        console.error('Failed to update code group:', error)
      }
    }

    const handleUpdateCodeDetail = async (data) => {
      try {
        const updatedDetail = await api.updateCodeDetail(selectedDetailForEdit.value.id, data)
        const index = specificCodes.value.findIndex(code => code.id === updatedDetail.id)
        if (index !== -1) {
          specificCodes.value[index] = {
            id: updatedDetail.id,
            parentCode: selectedGroup.value.codeGroupId,
            childCode: updatedDetail.codeDetailId,
            name: updatedDetail.name
          }
        }
        showUpdateDetailModal.value = false
      } catch (error) {
        alert(error.message)
        console.error('Failed to update code detail:', error)
      }
    }

    return {
      commonCodes,
      specificCodes,
      selectCommonCode,
      handleAddCommonCode,
      handleDeleteCommonCode,
      handleAddSpecificCode,
      handleEditCommonCode,
      handleEditSpecificCode,
      handleDeleteSpecificCode,
      isLoading,
      error,
      selectedGroupId,
      isLoadingDetails,
      detailsError,
      showCreateGroupModal,
      showCreateDetailModal,
      handleCreateCodeGroup,
      handleCreateCodeDetail,
      selectedGroup,
      formatDate,
      showUpdateGroupModal,
      showUpdateDetailModal,
      selectedGroupForEdit,
      selectedDetailForEdit,
      handleUpdateCodeGroup,
      handleUpdateCodeDetail
    }
  }
}
</script>