package com.siyoungbyun.buddyserver.petservice.controller;


import com.siyoungbyun.buddyserver.global.response.SuccessWithDataResponse;
import com.siyoungbyun.buddyserver.petservice.domain.PetService;
import com.siyoungbyun.buddyserver.petservice.request.CreatePetServiceRequest;
import com.siyoungbyun.buddyserver.petservice.service.PetServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/pet-services")
public class PetServiceController {

    @Autowired
    private PetServiceService petServiceService;

    @PostMapping
    public ResponseEntity<SuccessWithDataResponse<PetService>> createPetService(@RequestAttribute("userId") Long userId, @RequestBody CreatePetServiceRequest createPetServiceRequest) {
        // TODO: Need a proper response dto
        PetService petService = petServiceService.createPetService(userId, createPetServiceRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessWithDataResponse<>("펫시터 서비스 생성 성공", petService));

    }

    @GetMapping
    public ResponseEntity<SuccessWithDataResponse<List<PetService>>> getAllPetServices() {
        // TODO: Implement pagination
        // TODO: Need a proper response dto
        List<PetService> petServices = petServiceService.getAllPetServices();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessWithDataResponse<>("펫 서비스 목록 조회 성공", petServices));
    }
}
