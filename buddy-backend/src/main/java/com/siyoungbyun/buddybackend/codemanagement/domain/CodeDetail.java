package com.siyoungbyun.buddybackend.codemanagement.domain;

import com.siyoungbyun.buddybackend.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
//@Table(uniqueConstraints = @UniqueConstraint(columnNames={"name", "code_group_id"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_detail_internal_id")
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private String value;

    @Setter
    @Column(nullable = false)
    private String codeDetailId;

    @Setter
    private int sortOrder;

    @Setter
    @Column(nullable = false)
    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_group_internal_id")
    private CodeGroup codeGroup;

    @Builder
    private CodeDetail(String name, String value, String codeDetailId, CodeGroup codeGroup) {
        this.name = name;
        this.value = value;
        this.codeDetailId = codeDetailId;
        this.isActive = true;
        this.codeGroup = codeGroup;
    }
}
