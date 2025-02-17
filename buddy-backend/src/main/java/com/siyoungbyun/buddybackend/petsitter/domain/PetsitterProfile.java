package com.siyoungbyun.buddybackend.petsitter.domain;

import com.siyoungbyun.buddybackend.global.domain.BaseEntity;
import com.siyoungbyun.buddybackend.user.domain.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetsitterProfile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "petsitter_id")
    private Long id;

    @Setter
    @Column(nullable = false)
    private String description;

    @Setter
    @Column(nullable = false)
    private boolean isApproved;

    @Setter
    @OneToOne
    private User user;

    @Builder
    private PetsitterProfile(User user, String description) {
        this.description = description;
        this.user = user;
        this.isApproved = true;
    }
}
