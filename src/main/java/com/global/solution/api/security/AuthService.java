package com.global.solution.api.security;

import com.global.solution.api.user.User;
import com.global.solution.api.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TokenService tokenService;

    @Autowired
    AuthenticationManager authManager;

    public LoginRegisterRS login(LoginRQ loginRQ) {
        var userPass = new UsernamePasswordAuthenticationToken(loginRQ.email(), loginRQ.password());
        var auth = this.authManager.authenticate(userPass);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return new LoginRegisterRS(((User) auth.getPrincipal()).getName(), token);
    }

    @Transactional
    public LoginRegisterRS register(RegisterRQ registerRQ) {
        if (this.repository.findByEmail(registerRQ.email()).isPresent()) {
            throw new RuntimeException("Usuário já existe");
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(registerRQ.password()));
        user.setEmail(registerRQ.email());
        user.setName(registerRQ.name());
        this.repository.save(user);
        String token = this.tokenService.generateToken(user);
        return new LoginRegisterRS(user.getName(), token);
    }

}
