package com.siyoungbyun.buddybackend.petsitter.controller;

import com.siyoungbyun.buddybackend.auth.core.CustomUserDetails;
import com.siyoungbyun.buddybackend.global.dto.response.DataResponse;
import com.siyoungbyun.buddybackend.global.enums.ResponseStatus;
import com.siyoungbyun.buddybackend.petsitter.domain.PetsitterProfile;
import com.siyoungbyun.buddybackend.petsitter.dto.response.PetServiceResponse;
import com.siyoungbyun.buddybackend.petsitter.service.PetServiceService;
import com.siyoungbyun.buddybackend.petsitter.service.PetsitterProfileService;
import com.siyoungbyun.buddybackend.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/pet-services")
public class PetServiceController {

    @Autowired
    private PetServiceService petServiceService;

    @GetMapping
    public ResponseEntity<DataResponse<List<PetServiceResponse>>> getPetServices() {
        List<PetServiceResponse> petServices = petServiceService.getAllPetServices()
                .stream()
                .map(petService -> PetServiceResponse.fromEntity(petService))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "펫 서비스 조회 성공",
                        petServices));
    }
}