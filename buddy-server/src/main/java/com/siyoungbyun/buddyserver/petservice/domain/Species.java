package com.siyoungbyun.buddyserver.petservice.domain;

import com.siyoungbyun.buddyserver.petservice.enums.AnimalSpecies;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Species {

    @Id
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AnimalSpecies name;
}
