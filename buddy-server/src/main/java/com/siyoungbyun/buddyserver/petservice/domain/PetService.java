package com.siyoungbyun.buddyserver.petservice.domain;

import com.siyoungbyun.buddyserver.global.domain.BaseEntity;
import com.siyoungbyun.buddyserver.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.Set;

import com.siyoungbyun.buddyserver.petservice.request.CreatePetServiceRequest;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetService extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_service_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(
            name = "pet_service_available_days_of_week",
            joinColumns = @JoinColumn(name = "pet_service_id")
    )
    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> availableDaysOfWeek;

//    @ManyToMany
//    @JoinTable(
//            name = "pet_service_species",
//            joinColumns = @JoinColumn(name = "pet_service_id"),
//            inverseJoinColumns = @JoinColumn(name = "name")
//    )
//    private Set<Species> availableSpecies;

    public static PetService from(CreatePetServiceRequest request, User owner) {

        PetService petService = new PetService();
        petService.name = request.getName();
        petService.description = request.getDescription();
        petService.price = request.getPrice();
        petService.owner = owner;
        petService.availableDaysOfWeek = Set.of(request.getAvailableDaysOfWeek());
//        petService.availableSpecies = Set.of(request.getAvailableAnimalSpecies());
        return petService;
    }
}