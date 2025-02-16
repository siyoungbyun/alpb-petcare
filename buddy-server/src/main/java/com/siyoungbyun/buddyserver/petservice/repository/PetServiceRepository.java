package com.siyoungbyun.buddyserver.petservice.repository;


import com.siyoungbyun.buddyserver.petservice.domain.PetService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetServiceRepository extends JpaRepository<PetService, Long> {
}
