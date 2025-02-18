package com.siyoungbyun.buddybackend.petsitter.dto.response;


import com.siyoungbyun.buddybackend.petsitter.domain.Reservation;
import com.siyoungbyun.buddybackend.petsitter.enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
public class ReservationResponse {

    private Long id;
    private ReservationStatus status;
    private LocalDate reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int totalPrice;
    private Long userId;
    private String userName;
    private Long petServiceId;
    private String petServiceName;

    static public ReservationResponse fromEntity(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .status(reservation.getStatus())
                .reservationDate(reservation.getReservationDate())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .totalPrice(reservation.getTotalPrice())
                .userId(reservation.getUser().getId())
                .userName(reservation.getUser().getName())
                .petServiceId(reservation.getPetService().getId())
                .petServiceName(reservation.getPetService().getName())
                .build();
    }
}
