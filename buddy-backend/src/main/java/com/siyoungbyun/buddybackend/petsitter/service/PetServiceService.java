package com.siyoungbyun.buddybackend.petsitter.service;

import com.siyoungbyun.buddybackend.global.exception.NotFoundException;
import com.siyoungbyun.buddybackend.petsitter.domain.PetService;
import com.siyoungbyun.buddybackend.petsitter.domain.PetsitterProfile;
import com.siyoungbyun.buddybackend.petsitter.dto.request.CreatePetServiceRequest;
import com.siyoungbyun.buddybackend.petsitter.repository.PetServiceRepository;
import com.siyoungbyun.buddybackend.petsitter.repository.PetsitterProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetServiceService {

    private final PetServiceRepository petServiceRepository;

    private final PetsitterProfileRepository petsitterProfileRepository;

    public PetServiceService(PetServiceRepository petServiceRepository, PetsitterProfileRepository petsitterProfileRepository) {
        this.petServiceRepository = petServiceRepository;
        this.petsitterProfileRepository = petsitterProfileRepository;
    }

    public PetService createPetService(
            PetsitterProfile profile, CreatePetServiceRequest createPetServiceRequest) {
        PetService service = PetService.builder()
                .name(createPetServiceRequest.getName())
                .description(createPetServiceRequest.getDescription())
                .price(createPetServiceRequest.getPrice())
                .serviceType(createPetServiceRequest.getServiceType())
                .isAllDay(createPetServiceRequest.isAllDay())
                .startTime(createPetServiceRequest.getStartTime())
                .endTime(createPetServiceRequest.getEndTime())
                .availableDays(createPetServiceRequest.getAvailableDays())
                .locations(createPetServiceRequest.getLocations())
                .animalTypes(createPetServiceRequest.getAnimalTypes())
                .petsitter(profile)
                .build();
        return petServiceRepository.save(service);
    }

    public List<PetService> getPetServicesByPetsitter(Long profileId) {
        Optional<PetsitterProfile> optProfile = petsitterProfileRepository.findById(profileId);
        if (optProfile.isEmpty()) {
            throw new NotFoundException("존재하지 않는 펫시터입니다.");
        }
        PetsitterProfile profile = optProfile.get();
        return profile.getPetServices();
    }

    public List<PetService> getAllPetServices() {
        return petServiceRepository.findAll();
    }
}
