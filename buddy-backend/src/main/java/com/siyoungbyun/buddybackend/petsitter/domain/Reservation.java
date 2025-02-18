package com.siyoungbyun.buddybackend.petsitter.domain;

import com.siyoungbyun.buddybackend.global.domain.BaseEntity;
import com.siyoungbyun.buddybackend.petsitter.enums.ReservationStatus;
import com.siyoungbyun.buddybackend.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Enumerated(EnumType.STRING)
    private ReservationStatus status = ReservationStatus.PENDING;

    private LocalDate reservationDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private int totalPrice;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "pet_service_id", nullable = false)
    private PetService petService;

    @Builder
    private Reservation(LocalDate reservationDate, LocalTime startTime, LocalTime endTime, int totalPrice,
    User user, PetService petService) {
        this.reservationDate = reservationDate;
        this.status = ReservationStatus.PENDING;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;
        this.user = user;
        this.petService = petService;
    }
}
