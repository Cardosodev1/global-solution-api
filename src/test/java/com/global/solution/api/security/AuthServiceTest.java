package com.global.solution.api.security;

import com.global.solution.api.security.rqrs.LoginRegisterRS;
import com.global.solution.api.security.rqrs.RegisterRQ;
import com.global.solution.api.user.User;
import com.global.solution.api.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("Teste de registro e auth")
    void register_ShouldRegisterUser_WhenEmailIsValid() {
        // arrange
        RegisterRQ request = new RegisterRQ("Novo Aluno", "novo@fiap.com.br", "123456");

        when(repository.findByEmail(request.email())).thenReturn(Optional.empty());

        when(passwordEncoder.encode(request.password())).thenReturn("senhaCriptografada");

        when(tokenService.generateToken(any(User.class))).thenReturn("token.fake.jwt");

        LoginRegisterRS response = authService.register(request);

        // asserts
        Assertions.assertNotNull(response);
        Assertions.assertEquals("Novo Aluno", response.name());
        Assertions.assertEquals("token.fake.jwt", response.token());

        // testa se o repository foi chamado 1 vez
        verify(repository, Mockito.times(1)).save(any(User.class));
    }
}