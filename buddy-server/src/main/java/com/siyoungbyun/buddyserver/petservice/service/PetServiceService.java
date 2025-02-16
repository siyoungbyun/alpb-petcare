package com.siyoungbyun.buddyserver.petservice.service;

import com.siyoungbyun.buddyserver.petservice.domain.PetService;
import com.siyoungbyun.buddyserver.petservice.repository.PetServiceRepository;
import com.siyoungbyun.buddyserver.petservice.request.CreatePetServiceRequest;
import com.siyoungbyun.buddyserver.user.domain.User;
import com.siyoungbyun.buddyserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceService {

    private final PetServiceRepository petServiceRepository;
    private final UserService userService;

    @Transactional
    public PetService createPetService(Long userId, CreatePetServiceRequest createPetServiceRequest) {
        User ownerUser = userService.getUserById(userId);
        PetService petService = PetService.from(createPetServiceRequest, ownerUser);
        petServiceRepository.save(petService);
        return petService;
    }

    public List<PetService> getAllPetServices() {
        return petServiceRepository.findAll();
    }
}
