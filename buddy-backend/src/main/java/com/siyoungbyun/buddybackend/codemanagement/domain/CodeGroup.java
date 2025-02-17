package com.siyoungbyun.buddybackend.codemanagement.domain;

import com.siyoungbyun.buddybackend.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"name", "codeGroupId"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CodeGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_group_id")
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(nullable = false)
    private String codeGroupId;

    @Setter
    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "codeGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CodeDetail> codeDetails;

    @Builder
    private CodeGroup(String name, String codeGroupId, String description) {
        this.name = name;
        this.codeGroupId = codeGroupId;
        this.description = description;
    }

    public void addCodeDetail(CodeDetail codeDetail) {
        this.codeDetails.add(codeDetail);
    }
}
