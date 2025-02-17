package com.siyoungbyun.buddybackend.petsitter.domain;


import com.siyoungbyun.buddybackend.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetService extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "petsitter_service_id")
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private String description;

    @Setter
    @Column(nullable = false)
    private int price;

    @Setter
    @Column(nullable = false)
    private String serviceType;

    @Setter
    @Column(nullable = false)
    private boolean isAllDay;

    @Setter
    private LocalDateTime startTime;

    @Setter
    private LocalDateTime endTime;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "schedule_days", joinColumns = @JoinColumn(name = "schedule_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private List<DayOfWeek> availableDays;

    @ElementCollection
    @CollectionTable(name = "available_locations", joinColumns = @JoinColumn(name = "location_id"))
    @Column(name = "location")
    private List<String> locations;

    @ElementCollection
    @CollectionTable(name = "available_animal_types", joinColumns = @JoinColumn(name = "animal_type_id"))
    @Column(name = "animal_type")
    private List<String> animalTypes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "petsitter_profile_id")
    private PetsitterProfile petsitter;

    @OneToMany(mappedBy = "petService")
    private List<Reservation> reservations = new ArrayList<>();

    @Builder
    private PetService(
            String name,
            String description,
            int price,
            String serviceType,
            boolean isAllDay,
            LocalDateTime startTime,
            LocalDateTime endTime,
            List<DayOfWeek> availableDays,
            List<String> locations,
            List<String> animalTypes,
            PetsitterProfile petsitter) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.serviceType = serviceType;
        this.isAllDay = isAllDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.availableDays = availableDays;
        this.locations = locations;
        this.animalTypes = animalTypes;
        this.petsitter = petsitter;
    }
}
