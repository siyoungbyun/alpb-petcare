package com.siyoungbyun.buddybackend.petsitter.dto.response;

import com.siyoungbyun.buddybackend.petsitter.domain.PetsitterProfile;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PetsitterProfileResponse {
    private Long id;
    private String description;

    static public PetsitterProfileResponse fromEntity(PetsitterProfile petsitterProfile) {
        return PetsitterProfileResponse.builder()
                .id(petsitterProfile.getId())
                .description(petsitterProfile.getDescription())
                .build();
    }
}
