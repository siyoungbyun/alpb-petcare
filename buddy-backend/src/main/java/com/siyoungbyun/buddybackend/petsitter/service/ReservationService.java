package com.siyoungbyun.buddybackend.petsitter.service;

import com.siyoungbyun.buddybackend.global.exception.NotFoundException;
import com.siyoungbyun.buddybackend.petsitter.domain.PetService;
import com.siyoungbyun.buddybackend.petsitter.domain.PetsitterProfile;
import com.siyoungbyun.buddybackend.petsitter.domain.Reservation;
import com.siyoungbyun.buddybackend.petsitter.dto.request.CreateReservationRequest;
import com.siyoungbyun.buddybackend.petsitter.enums.ReservationStatus;
import com.siyoungbyun.buddybackend.petsitter.repository.PetServiceRepository;
import com.siyoungbyun.buddybackend.petsitter.repository.PetsitterProfileRepository;
import com.siyoungbyun.buddybackend.petsitter.repository.ReservationRepository;
import com.siyoungbyun.buddybackend.user.domain.User;
import com.siyoungbyun.buddybackend.user.repository.UserRepository;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetServiceRepository petServiceRepository;

    @Autowired
    private PetsitterProfileRepository petsitterProfileRepository;

    public Reservation createReservation(
            Long userId, Long petServiceId, CreateReservationRequest createReservationRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));
        PetService petService = petServiceRepository.findById(petServiceId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 펫 서비스입니다."));

        Reservation reservation = Reservation.builder()
                .reservationDate(createReservationRequest.getReservationDate())
                .startTime(createReservationRequest.getStartTime())
                .endTime(createReservationRequest.getEndTime())
                .user(user)
                .petService(petService)
                .build();

        return reservationRepository.save(reservation);
    }

    public Reservation confirmReservation(Long reservationId, boolean isConfirmed) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 예약입니다."));

        if (isConfirmed) {
            reservation.setStatus(ReservationStatus.CONFIRMED);
        } else {
            reservation.setStatus(ReservationStatus.REJECTED);
        }

        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservationsByPetsitter(Long petsitterId) {
        PetsitterProfile profile = petsitterProfileRepository.findById(petsitterId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 펫시터입니다."));
        return reservationRepository.findByPetService_Petsitter(profile);
    }
}