package com.siyoungbyun.buddybackend.petsitter.controller;

import com.siyoungbyun.buddybackend.auth.core.CustomUserDetails;
import com.siyoungbyun.buddybackend.global.dto.response.DataResponse;
import com.siyoungbyun.buddybackend.global.enums.ResponseStatus;
import com.siyoungbyun.buddybackend.petsitter.domain.Reservation;
import com.siyoungbyun.buddybackend.petsitter.dto.request.CreateReservationRequest;
import com.siyoungbyun.buddybackend.petsitter.dto.response.PetServiceResponse;
import com.siyoungbyun.buddybackend.petsitter.dto.response.ReservationResponse;
import com.siyoungbyun.buddybackend.petsitter.service.PetServiceService;
import com.siyoungbyun.buddybackend.petsitter.service.ReservationService;
import com.siyoungbyun.buddybackend.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/pet-services")
public class PetServiceController {

    @Autowired
    private PetServiceService petServiceService;

    @Autowired
    private ReservationService reservationService;

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

    @PostMapping("/{petServiceId}/reservations")
    public ResponseEntity<DataResponse<ReservationResponse>> bookReservation(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long petServiceId,
            @RequestBody CreateReservationRequest createReservationRequest
            ) {
        User user = customUserDetails.getUser();
        Reservation reservation = reservationService.createReservation(
                user.getId(), petServiceId, createReservationRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "펫 서비스 예약 신청 성공",
                        ReservationResponse.fromEntity(reservation)));
    }

    @PostMapping("/{petServiceId}/reservations/{reservationId}/confirm")
    public ResponseEntity<DataResponse<ReservationResponse>> confirmReservation(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long petServiceId,
            @PathVariable Long reservationId,
            @RequestParam boolean confirm
    ) {
        Reservation updatedReservation = reservationService.confirmReservation(reservationId, confirm);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "펫 서비스 예약 상태 변경 성공",
                        ReservationResponse.fromEntity(updatedReservation)));
    }
}