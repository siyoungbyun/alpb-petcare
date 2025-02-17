package com.siyoungbyun.buddybackend.codemanagement.service;

import com.siyoungbyun.buddybackend.codemanagement.domain.CodeGroup;
import com.siyoungbyun.buddybackend.codemanagement.dto.request.CreateCodeGroupRequest;
import com.siyoungbyun.buddybackend.codemanagement.dto.request.UpdateCodeGroupRequest;
import com.siyoungbyun.buddybackend.codemanagement.repository.CodeDetailRepository;
import com.siyoungbyun.buddybackend.codemanagement.repository.CodeGroupRepository;
import com.siyoungbyun.buddybackend.global.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CodeManagementService {

    private final CodeGroupRepository codeGroupRepository;

    private final CodeDetailRepository codeDetailRepository;

    public CodeManagementService(CodeGroupRepository codeGroupRepository,
                                 CodeDetailRepository codeDetailRepository) {
        this.codeGroupRepository = codeGroupRepository;
        this.codeDetailRepository = codeDetailRepository;
    }

    public CodeGroup createCodeGroup(CreateCodeGroupRequest createCodeGroupRequest) {
        CodeGroup codeGroup = CodeGroup.builder()
                .name(createCodeGroupRequest.getName())
                .codeGroupId(createCodeGroupRequest.getCodeGroupId())
                .description(createCodeGroupRequest.getDescription())
                .build();
        return codeGroupRepository.save(codeGroup);
    }

    // TODO: pagination
    public List<CodeGroup> getCodeGroups() {
        return codeGroupRepository.findAll();
    }

    public CodeGroup getCodeGroup(Long codeGroupId) {
        Optional<CodeGroup> optCodeGroup = codeGroupRepository.findById(codeGroupId);
        if (optCodeGroup.isEmpty()) {
            throw new NotFoundException("존재하지 않는 코드 그룹입니다.");
        }
        return optCodeGroup.get();
    }

    public CodeGroup updateCodeGroup(Long codeGroupId, UpdateCodeGroupRequest updateCodeGroupRequest) {
        CodeGroup codeGroup = getCodeGroup(codeGroupId);
        if (updateCodeGroupRequest.getCodeGroupId() != null) {
            codeGroup.setCodeGroupId(updateCodeGroupRequest.getCodeGroupId());
        }
        if (updateCodeGroupRequest.getName() != null) {
            codeGroup.setName(updateCodeGroupRequest.getName());
        }
        if (updateCodeGroupRequest.getDescription() != null) {
            codeGroup.setDescription(updateCodeGroupRequest.getDescription());
        }
        return codeGroupRepository.save(codeGroup);
    }

    public void deleteCodeGroup(Long codeGroupId) {
        CodeGroup codeGroup = getCodeGroup(codeGroupId);
        codeGroupRepository.delete(codeGroup);
    }
}
