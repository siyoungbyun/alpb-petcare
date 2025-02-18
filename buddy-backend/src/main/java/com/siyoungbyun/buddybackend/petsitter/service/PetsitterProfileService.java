package com.siyoungbyun.buddybackend.petsitter.service;

import com.siyoungbyun.buddybackend.global.exception.AlreadyExistsException;
import com.siyoungbyun.buddybackend.global.exception.NotFoundException;
import com.siyoungbyun.buddybackend.petsitter.domain.PetsitterProfile;
import com.siyoungbyun.buddybackend.petsitter.dto.request.CreatePetsitterRequest;
import com.siyoungbyun.buddybackend.petsitter.repository.PetsitterProfileRepository;
import com.siyoungbyun.buddybackend.user.domain.User;
import com.siyoungbyun.buddybackend.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PetsitterProfileService {

    private final PetsitterProfileRepository petsitterProfileRepository;

    private final UserRepository userRepository;

    public PetsitterProfileService(PetsitterProfileRepository petsitterProfileRepository,
                                   UserRepository userRepository) {
        this.petsitterProfileRepository = petsitterProfileRepository;
        this.userRepository = userRepository;
    }

    public List<PetsitterProfile> getAllPetsitterProfiles() {
        return petsitterProfileRepository.findAll();
    }

    @Transactional
    public PetsitterProfile getPetsitterProfile(User user) {
        if (user.getPetsitterProfile() == null) {
            throw new NotFoundException("프로필이 없습니다.");
        }
        return user.getPetsitterProfile();
    }

    @Transactional
    public PetsitterProfile createPetsitterProfile(User user, CreatePetsitterRequest createPetsitterRequest) {
        if (user.getPetsitterProfile() != null) {
            throw new AlreadyExistsException("프로필이 이미 존재합니다.");
        }
        PetsitterProfile profile = PetsitterProfile
                .builder()
                .description(createPetsitterRequest.getDescription())
                .user(user)
                .build();
        petsitterProfileRepository.save(profile);
        user.setPetsitterProfile(profile);
        userRepository.save(user);
        return profile;
    }

    @Transactional
    public void deletePetsitterProfile(User user) {
        PetsitterProfile petsitterProfile = user.getPetsitterProfile();
        if (petsitterProfile == null) {
            throw new NotFoundException("설정된 프로필이 없습니다.");
        }
        user.setPetsitterProfile(null);
        userRepository.save(user);
    }
}
