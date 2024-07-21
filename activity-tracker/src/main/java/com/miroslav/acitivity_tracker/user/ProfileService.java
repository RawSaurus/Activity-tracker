package com.miroslav.acitivity_tracker.user;

import com.miroslav.acitivity_tracker.user.repository.ProfileRepository;
import com.miroslav.acitivity_tracker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
}
