package com.w1zer.service;

import com.w1zer.entity.Profile;
import com.w1zer.entity.Role;
import com.w1zer.entity.RoleName;
import com.w1zer.exception.NotFoundException;
import com.w1zer.payload.ProfileRequest;
import com.w1zer.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProfileService {

    //    private static final String PROFILE_WITH_LOGIN_ALREADY_EXISTS = "Profile with login '%s' already exists";
//    private static final String PROFILE_WITH_ID_NOT_FOUND = "Profile with id '%d' not found";
    private static final String PROFILE_WITH_LOGIN_NOT_FOUND = "Profile with login '%s' not found";

    private final ProfileRepository profileRepository;
    private final RoleService roleService;
//    private final ProfileMapper profileMapper;
//    private final PasswordEncoder passwordEncoder;

    public ProfileService(ProfileRepository profileRepository,
                          RoleService roleService)
//            , ProfileMapper profileMapper,
//                          PasswordEncoder passwordEncoder)
    {
        this.profileRepository = profileRepository;
//        this.profileMapper = profileMapper;
//        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    //    public List<ProfileResponse> getAll(String login) {
//        if (login == null) {
//            return profileMapper.mapToProfileResponseList(profileRepository.findAll());
//        }
//        Profile profile = profileRepository.findByLogin(login).orElseThrow(
//                () -> new NotFoundException(PROFILE_WITH_LOGIN_NOT_FOUND.formatted(login))
//        );
//        return profileMapper.mapToProfileResponseList(profile);
//    }
//
//    public ProfileResponse getById(Long id) {
//        Profile profile = profileRepository.findById(id).orElseThrow(
//                () -> new NotFoundException(PROFILE_WITH_ID_NOT_FOUND.formatted(id))
//        );
//        return profileMapper.mapToProfileResponse(profile);
//    }
//
//    public ProfileResponse insert(ProfileRequest profileRequest) {
//        String login = profileRequest.login();
//        if (profileRepository.existsByLogin(login)) {
//            throw new ProfileAlreadyExistsException(PROFILE_WITH_LOGIN_ALREADY_EXISTS.formatted(login));
//        }
//        Profile profile = getProfileWithEncodedPassword(profileRequest);
//        Profile result = profileRepository.save(profile);
//        return profileMapper.mapToProfileResponse(result);
//    }
//
//    public void delete(Long id) {
//        if (!profileRepository.existsById(id)) {
//            throw new NotFoundException(PROFILE_WITH_ID_NOT_FOUND.formatted(id));
//        }
//        profileRepository.deleteById(id);
//    }
//
//    public ProfileResponse update(Long id, ProfileRequest profileRequest) {
//        if (!profileRepository.existsById(id)) {
//            throw new NotFoundException(PROFILE_WITH_ID_NOT_FOUND.formatted(id));
//        }
//        String login = profileRequest.login();
//        Optional<Profile> profileWithSameLogin = profileRepository.findByLogin(login);
//        if (profileWithSameLogin.isPresent() && !Objects.equals(profileWithSameLogin.get().getId(), id)) {
//            throw new ProfileAlreadyExistsException(PROFILE_WITH_LOGIN_ALREADY_EXISTS.formatted(login));
//        }
//        Profile profile = getProfileWithEncodedPasswordAndId(id, profileRequest);
//        Profile result = profileRepository.save(profile);
//        return profileMapper.mapToProfileResponse(result);
//    }
//
//    public Profile getProfileById(Long idProfile) {
//        return profileRepository.findById(idProfile).orElseThrow(
//                () -> new NotFoundException(PROFILE_WITH_ID_NOT_FOUND.formatted(idProfile))
//        );
//    }
//
//    private Profile getProfileWithEncodedPassword(ProfileRequest profileRequest) {
//        Profile profile = profileMapper.mapToProfile(profileRequest);
//        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
//        return profile;
//    }
//
//    private Profile getProfileWithEncodedPasswordAndId(Long id, ProfileRequest profileRequest) {
//        Profile profile = profileMapper.mapToProfile(id, profileRequest);
//        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
//        return profile;
//    }
    public void promote(ProfileRequest profileRequest) {
        Profile profile = findByLogin(profileRequest.login());
        Set<Role> roles = profile.getRoles();
        Role user = roleService.findByName(RoleName.ROLE_USER);
        if (!(roles.size() == 1 && roles.contains(user))) {
            throw new RuntimeException("Profile must have 1 role: USER");
        }
        Role moderator = roleService.findByName(RoleName.ROLE_MODERATOR);
        profile.setRoles(Set.of(user, moderator));
        profileRepository.save(profile);
    }

    public void demote(ProfileRequest profileRequest) {
        Profile profile = findByLogin(profileRequest.login());
        Set<Role> roles = profile.getRoles();
        Role user = roleService.findByName(RoleName.ROLE_USER);
        Role moderator = roleService.findByName(RoleName.ROLE_MODERATOR);
        if (!(roles.size() == 2 && roles.contains(moderator) && roles.contains(user))) {
            throw new RuntimeException("Profile must have 2 roles: USER, MODERATOR");
        }
        profile.setRoles(Set.of(user));
        profileRepository.save(profile);
    }

    private Profile findByLogin(String login) {
        return profileRepository.findByLogin(login).orElseThrow(
                () -> new NotFoundException(PROFILE_WITH_LOGIN_NOT_FOUND.formatted(login))
        );
    }
}
