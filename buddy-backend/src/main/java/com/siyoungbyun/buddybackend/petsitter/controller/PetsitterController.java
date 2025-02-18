package com.siyoungbyun.buddybackend.petsitter.controller;


import com.siyoungbyun.buddybackend.global.dto.response.DataResponse;
import com.siyoungbyun.buddybackend.global.enums.ResponseStatus;
import com.siyoungbyun.buddybackend.petsitter.dto.response.PetsitterProfileResponse;
import com.siyoungbyun.buddybackend.petsitter.service.PetsitterProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/petsitters")
public class PetsitterController {

    @Autowired
    private PetsitterProfileService petsitterProfileService;

    @GetMapping
    public ResponseEntity<DataResponse<List<PetsitterProfileResponse>>> getPetsitters() {
        List<PetsitterProfileResponse> profiles = petsitterProfileService.getAllPetsitterProfiles()
                .stream()
                .map(petsitterProfile -> PetsitterProfileResponse.fromEntity(petsitterProfile))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "펫시터 목록 조회 성공",
                        profiles));
    }
}
