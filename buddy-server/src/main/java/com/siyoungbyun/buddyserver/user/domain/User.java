package com.siyoungbyun.buddyserver.user.domain;

import com.siyoungbyun.buddyserver.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Setter
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;

    private User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User of(String email, String password, String name) {
        return new User(email, password, name);
    };

}
