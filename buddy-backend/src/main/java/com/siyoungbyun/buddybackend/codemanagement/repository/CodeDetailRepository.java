package com.siyoungbyun.buddybackend.codemanagement.repository;

import com.siyoungbyun.buddybackend.codemanagement.domain.CodeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeDetailRepository extends JpaRepository<CodeDetail, Long> {
}
