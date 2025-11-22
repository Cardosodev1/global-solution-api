package com.global.solution.api.user;

import com.global.solution.api.user.rqrs.UserRS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UserService service;

    @Test
    @DisplayName("Teste de perfil do usuário")
    void getMyProfile_ShouldReturnUserRS() {
        // arrange
        User fakeUser = new User("rm556715", "hassan@fiap.com.br", "123987");

        UserRS result = service.getMyProfile(fakeUser);

        // asserts
        Assertions.assertNotNull(result);
        Assertions.assertEquals("rm556715", result.name());
        Assertions.assertEquals("hassan@fiap.com.br", result.email());
    }
}