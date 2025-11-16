package com.global.solution.api.user;

import com.global.solution.api.exception.EmailAlreadyExistsException;
import com.global.solution.api.exception.ResourceNotFoundException;
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
    public UserRS updateMyProfile(UserUpdateRQ request, User userFromAuth) {
        User managedUser = repository.findById(userFromAuth.getId()).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado na sessão"));
        if (request.email() != null && !request.email().equals(managedUser.getEmail())) {
            if (repository.findByEmail(request.email()).isPresent()) {
                throw new EmailAlreadyExistsException("Email já está em uso");
            }
        }
        managedUser.update(request, passEncoder);
        return new UserRS(managedUser);
    }

    @Transactional
    public void deleteMyProfile(User user) {
        repository.deleteById(user.getId());
    }

}
