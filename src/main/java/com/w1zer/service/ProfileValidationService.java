package com.w1zer.service;

import com.w1zer.exception.ProfileAlreadyExistsException;
import com.w1zer.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileValidationService {
    private static final String PROFILE_WITH_EMAIL_ALREADY_EXISTS = "Profile with email '%s' already exists";
    private static final String PROFILE_WITH_LOGIN_ALREADY_EXISTS = "Profile with login '%s' already exists";

    private final ProfileRepository profileRepository;

    public ProfileValidationService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public void validateEmail(String email) {
        if (profileRepository.existsByEmail(email)) {
            throw new ProfileAlreadyExistsException(PROFILE_WITH_EMAIL_ALREADY_EXISTS.formatted(email));
        }
    }

    public void validateLogin(String login) {
        if (profileRepository.existsByLogin(login)) {
            throw new ProfileAlreadyExistsException(PROFILE_WITH_LOGIN_ALREADY_EXISTS.formatted(login));
        }
    }
}