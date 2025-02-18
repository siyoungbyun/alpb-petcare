package com.siyoungbyun.buddybackend.petsitter.dto.response;

import com.siyoungbyun.buddybackend.petsitter.domain.PetsitterProfile;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PetsitterProfileResponse {
    private Long id;
    private String name;
    private String phoneNumber;
    private String description;

    static public PetsitterProfileResponse fromEntity(PetsitterProfile petsitterProfile) {
        return PetsitterProfileResponse.builder()
                .id(petsitterProfile.getId())
                .name(petsitterProfile.getUser().getName())
                .phoneNumber(petsitterProfile.getUser().getPhoneNumber())
                .description(petsitterProfile.getDescription())
                .build();
    }
}
