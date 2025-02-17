package com.siyoungbyun.buddybackend.user.domain;

import com.siyoungbyun.buddybackend.global.domain.BaseEntity;
import com.siyoungbyun.buddybackend.petsitter.domain.PetsitterProfile;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false, length=11)
    private String phoneNumber;

    @Setter
    @Column(nullable = false)
    private boolean isAdmin;

    @Setter
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "petsitter_id")
    private PetsitterProfile petsitterProfile;

    @Builder
    private User(String email, String password, String name, String phoneNumber, boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
    }
}
