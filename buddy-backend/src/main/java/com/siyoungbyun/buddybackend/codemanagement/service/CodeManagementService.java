package com.siyoungbyun.buddybackend.codemanagement.service;

import com.siyoungbyun.buddybackend.codemanagement.domain.CodeDetail;
import com.siyoungbyun.buddybackend.codemanagement.domain.CodeGroup;
import com.siyoungbyun.buddybackend.codemanagement.dto.request.CreateCodeDetailRequest;
import com.siyoungbyun.buddybackend.codemanagement.dto.request.CreateCodeGroupRequest;
import com.siyoungbyun.buddybackend.codemanagement.dto.request.UpdateCodeDetailRequest;
import com.siyoungbyun.buddybackend.codemanagement.dto.request.UpdateCodeGroupRequest;
import com.siyoungbyun.buddybackend.codemanagement.repository.CodeDetailRepository;
import com.siyoungbyun.buddybackend.codemanagement.repository.CodeGroupRepository;
import com.siyoungbyun.buddybackend.global.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public CodeDetail createCodeDetail(Long codeGroupId, CreateCodeDetailRequest createCodeDetailRequest) {
        CodeGroup codeGroup = getCodeGroup(codeGroupId);
        CodeDetail codeDetail = CodeDetail.builder()
                .name(createCodeDetailRequest.getName())
                .value(createCodeDetailRequest.getValue())
                .codeDetailId(createCodeDetailRequest.getCodeDetailId())
                .codeGroup(codeGroup)
                .build();
        codeDetailRepository.save(codeDetail);
        codeGroup.addCodeDetail(codeDetail);
        return codeDetail;
    }

    // TODO: pagination
    public List<CodeDetail> getCodeDetailsByCodeGroup(Long codeGroupId) {
        CodeGroup codeGroup = getCodeGroup(codeGroupId);
        return codeGroup.getCodeDetails();
    }

    public CodeDetail getCodeDetail(Long codeDetailId) {
        Optional<CodeDetail> optCodeDetail = codeDetailRepository.findById(codeDetailId);
        if (optCodeDetail.isEmpty()) {
            throw new NotFoundException("존재하지 않는 코드 그룹입니다.");
        }
        return optCodeDetail.get();
    }

    public CodeDetail updateCodeDetail(Long codeDetailId, UpdateCodeDetailRequest updateCodeDetailRequest) {
        CodeDetail codeDetail = getCodeDetail(codeDetailId);
        if (updateCodeDetailRequest.getName() != null) {
            codeDetail.setName(updateCodeDetailRequest.getName());
        }
        if (updateCodeDetailRequest.getValue() != null) {
            codeDetail.setValue(updateCodeDetailRequest.getValue());
        }
        if (updateCodeDetailRequest.getCodeDetailId() != null) {
            codeDetail.setCodeDetailId(updateCodeDetailRequest.getCodeDetailId());
        }
        // TODO: Handle sortOrder and isActive accordingly
        return codeDetailRepository.save(codeDetail);
    }

    public void deleteCodeDetail(Long codeDetailId) {
        CodeDetail codeDetail = getCodeDetail(codeDetailId);
        codeDetailRepository.delete(codeDetail);
    }
}
