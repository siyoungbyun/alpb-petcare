package com.siyoungbyun.buddybackend.user.controller;


import com.siyoungbyun.buddybackend.auth.core.CustomUserDetails;
import com.siyoungbyun.buddybackend.global.dto.response.BaseResponse;
import com.siyoungbyun.buddybackend.global.dto.response.DataResponse;
import com.siyoungbyun.buddybackend.global.enums.ResponseStatus;
import com.siyoungbyun.buddybackend.petsitter.domain.PetService;
import com.siyoungbyun.buddybackend.petsitter.domain.PetsitterProfile;
import com.siyoungbyun.buddybackend.petsitter.dto.request.CreatePetServiceRequest;
import com.siyoungbyun.buddybackend.petsitter.dto.request.CreatePetsitterRequest;
import com.siyoungbyun.buddybackend.petsitter.dto.response.PetServiceResponse;
import com.siyoungbyun.buddybackend.petsitter.dto.response.PetsitterProfileResponse;
import com.siyoungbyun.buddybackend.petsitter.dto.response.ReservationResponse;
import com.siyoungbyun.buddybackend.petsitter.service.PetServiceService;
import com.siyoungbyun.buddybackend.petsitter.service.PetsitterProfileService;
import com.siyoungbyun.buddybackend.petsitter.service.ReservationService;
import com.siyoungbyun.buddybackend.user.domain.User;
import com.siyoungbyun.buddybackend.user.dto.request.UpdateSelfRequest;
import com.siyoungbyun.buddybackend.user.dto.response.UserResponse;
import com.siyoungbyun.buddybackend.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/me")
public class MeController {

    @Autowired
    private UserService userService;

    @Autowired
    private PetsitterProfileService petsitterProfileService;

    @Autowired
    private PetServiceService petServiceService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<DataResponse<UserResponse>> getSelf(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "내 정보 조회 성공",
                        UserResponse.fromEntity(customUserDetails.getUser())));
    }

    @PatchMapping
    public ResponseEntity<DataResponse<UserResponse>> updateSelf(@RequestBody UpdateSelfRequest updateSelfRequest,
                                                   @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = userService.UpdateUser(customUserDetails.getId(), updateSelfRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "내 정보 수정 성공",
                        UserResponse.fromEntity(user)));
    }

    @DeleteMapping
    public ResponseEntity<BaseResponse> deleteSelf(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        userService.deleteUser(customUserDetails.getId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse(ResponseStatus.SUCCESS, "회원탈퇴 성공"));
    }

    @GetMapping("/petsitter-profile")
    public ResponseEntity<DataResponse<PetsitterProfileResponse>> getPetsitterProfile(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        PetsitterProfile profile = petsitterProfileService.getPetsitterProfile(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "내 펫시터 프로필 조회 성공",
                        PetsitterProfileResponse.fromEntity(profile)));
    }

    @PostMapping("/petsitter-profile")
    public ResponseEntity<DataResponse<PetsitterProfileResponse>> createPetsitterProfile(
            @RequestBody CreatePetsitterRequest createPetsitterRequest,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        PetsitterProfile profile = petsitterProfileService.createPetsitterProfile(user, createPetsitterRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "내 펫시터 프로필 생성 성공",
                        PetsitterProfileResponse.fromEntity(profile)));
    }

    @DeleteMapping("/petsitter-profile")
    public ResponseEntity<BaseResponse> deletePetsitterProfile(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        petsitterProfileService.deletePetsitterProfile(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new BaseResponse(ResponseStatus.SUCCESS, "내 펫시터 프로필 삭제 성공"));
    }

    @GetMapping("/petsitter-profile/reservations")
    public ResponseEntity<DataResponse<List<ReservationResponse>>> getIncomingReservations(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        PetsitterProfile profile = petsitterProfileService.getPetsitterProfile(user);
        List<ReservationResponse> reservations = reservationService.getReservationsByPetsitter(profile.getId())
                .stream()
                .map(reservation -> ReservationResponse.fromEntity(reservation))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "예약 목록 조회 성공",
                        reservations));
    }

    @GetMapping("/pet-services")
    public ResponseEntity<DataResponse<List<PetServiceResponse>>> getPetServices(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        PetsitterProfile profile = petsitterProfileService.getPetsitterProfile(user);
        List<PetServiceResponse> petServices = petServiceService.getPetServicesByPetsitter(profile.getId())
                .stream()
                .map(petService -> PetServiceResponse.fromEntity(petService))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "펫 서비스 조회 성공",
                        petServices));
    }

    @PostMapping("/pet-services")
    public ResponseEntity<DataResponse<PetServiceResponse>> createPetService(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody CreatePetServiceRequest createPetServiceRequest) {
        User user = customUserDetails.getUser();
        PetsitterProfile profile = petsitterProfileService.getPetsitterProfile(user);
        PetService service = petServiceService.createPetService(profile, createPetServiceRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new DataResponse<>(ResponseStatus.SUCCESS, "펫 서비스 생성 성공",
                        PetServiceResponse.fromEntity(service)));
    }
}
