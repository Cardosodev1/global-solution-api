package com.global.solution.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passEncoder;

    public UserRS getMyProfile(User user) {
        return new UserRS(user);
    }

    @Transactional
    public UserRS updateMyProfile(UserUpdateRQ request, User user) {
        if (request.email() != null && !request.email().equals(user.getEmail())) {
            if (repository.findByEmail(request.email()).isPresent()) {
                throw new RuntimeException("Email já está em uso.");
            }
        }
        user.update(request, passEncoder);
        return new UserRS(user);
    }

    @Transactional
    public void deleteMyProfile(User user) {
        repository.deleteById(user.getId());
    }

}
