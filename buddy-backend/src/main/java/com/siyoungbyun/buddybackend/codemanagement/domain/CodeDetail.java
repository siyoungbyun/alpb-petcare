package com.siyoungbyun.buddybackend.codemanagement.domain;

import com.siyoungbyun.buddybackend.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"name", "code_group_id"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_detail_id")
    private Long id;

    @Setter
    @Column(nullable = false)
    private Long name;

    @Setter
    @Column(nullable = false)
    private Long value;

    @Setter
    @Column(nullable = false)
    private String codeGroupId;

    @Setter
    @Column(nullable = false)
    private int sortOrder;

    @Setter
    @Column(nullable = false)
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "code_group_id")
    private CodeGroup codeGroup;
}
