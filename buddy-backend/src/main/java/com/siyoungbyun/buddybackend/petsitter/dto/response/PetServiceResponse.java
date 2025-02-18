package com.siyoungbyun.buddybackend.petsitter.dto.response;

import com.siyoungbyun.buddybackend.petsitter.domain.PetService;
import lombok.Builder;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class PetServiceResponse {

    private long id;
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
    private Long petsitterId;

    static public PetServiceResponse fromEntity(PetService petService) {
        return PetServiceResponse.builder()
                .id(petService.getId())
                .name(petService.getName())
                .description(petService.getDescription())
                .price(petService.getPrice())
                .serviceType(petService.getServiceType())
                .isAllDay(petService.isAllDay())
                .startTime(petService.getStartTime())
                .endTime(petService.getEndTime())
                .availableDays(petService.getAvailableDays())
                .locations(petService.getLocations())
                .animalTypes(petService.getAnimalTypes())
                .petsitterId(petService.getPetsitter().getId())
                .build();
    }
}
