package com.siyoungbyun.buddybackend.petsitter.dto.request;


import com.siyoungbyun.buddybackend.petsitter.domain.PetsitterProfile;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CreatePetServiceRequest {
    private String name;
    private String description;
    private int price;
    private String serviceType;
    private boolean isAllDay;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<DayOfWeek> availableDays;
    private List<String> locations;
    private List<String> animalTypes;
}
