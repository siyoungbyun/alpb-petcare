package com.siyoungbyun.buddybackend.petsitter.repository;

import com.siyoungbyun.buddybackend.petsitter.domain.PetsitterProfile;
import com.siyoungbyun.buddybackend.petsitter.domain.Reservation;
import com.siyoungbyun.buddybackend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByPetService_Petsitter(PetsitterProfile petsitterProfile);

    List<Reservation> findByUser(User user);
}
