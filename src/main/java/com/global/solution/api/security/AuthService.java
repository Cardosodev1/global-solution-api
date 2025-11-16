package com.global.solution.api.security;

import com.global.solution.api.exception.EmailAlreadyExistsException;
import com.global.solution.api.security.rqrs.LoginRQ;
import com.global.solution.api.security.rqrs.LoginRegisterRS;
import com.global.solution.api.security.rqrs.RegisterRQ;
import com.global.solution.api.user.User;
import com.global.solution.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authManager;

    public LoginRegisterRS login(LoginRQ loginRQ) {
        var userPass = new UsernamePasswordAuthenticationToken(loginRQ.email(), loginRQ.password());
        var auth = this.authManager.authenticate(userPass);
        User user = (User) auth.getPrincipal();
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return new LoginRegisterRS(user.getId(), user.getName(), token);
    }

    @Transactional
    public LoginRegisterRS register(RegisterRQ registerRQ) {
        if (this.repository.findByEmail(registerRQ.email()).isPresent()) {
            throw new EmailAlreadyExistsException("Usuário já existe");
        }
        String encodedPass = passwordEncoder.encode(registerRQ.password());
        User user = new User(registerRQ.name(), registerRQ.email(), encodedPass);
        this.repository.save(user);
        String token = this.tokenService.generateToken(user);
        return new LoginRegisterRS(user.getId(), user.getName(), token);
    }

}
