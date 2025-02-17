package com.siyoungbyun.buddybackend.petsitter.repository;

import com.siyoungbyun.buddybackend.petsitter.domain.PetService;
import com.siyoungbyun.buddybackend.petsitter.domain.PetsitterProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetServiceRepository extends JpaRepository<PetService, Long> {
}
