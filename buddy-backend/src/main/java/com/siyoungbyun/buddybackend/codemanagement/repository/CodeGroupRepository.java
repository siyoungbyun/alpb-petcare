package com.siyoungbyun.buddybackend.codemanagement.repository;

import com.siyoungbyun.buddybackend.codemanagement.domain.CodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeGroupRepository extends JpaRepository<CodeGroup, Long> {
}
