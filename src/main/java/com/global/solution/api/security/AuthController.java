package com.global.solution.api.security;

import com.global.solution.api.security.rqrs.LoginRQ;
import com.global.solution.api.security.rqrs.LoginRegisterRS;
import com.global.solution.api.security.rqrs.RegisterRQ;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginRegisterRS> login(@RequestBody @Valid LoginRQ loginRQ){
        LoginRegisterRS response = authService.login(loginRQ);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/register")
    public ResponseEntity<LoginRegisterRS> register(@RequestBody @Valid RegisterRQ registerRQ){
        LoginRegisterRS response = authService.register(registerRQ);
        return ResponseEntity.ok(response);
    }

}
