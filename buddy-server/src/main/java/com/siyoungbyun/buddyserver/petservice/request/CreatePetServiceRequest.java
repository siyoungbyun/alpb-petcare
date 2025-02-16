package com.siyoungbyun.buddyserver.petservice.request;

import lombok.Getter;
import java.time.DayOfWeek;
import java.time.LocalTime;


@Getter
public class CreatePetServiceRequest {
    private String name;
    private String serviceType;
    private String availableLocation;
    private String description;
    // TODO: Implement available animal species
    // private Set<AnimalSpecies> availableAnimalSpecies;
    private int price;
    private DayOfWeek[] availableDaysOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
