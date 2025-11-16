package com.global.solution.api.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/me")
    public ResponseEntity<UserRS> getMyProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(service.getMyProfile(user));
    }

    @PutMapping("/me")
    public ResponseEntity<UserRS> updateMyProfile(@RequestBody @Valid UserUpdateRQ request, @AuthenticationPrincipal User user) {
        UserRS userRS = service.updateMyProfile(request, user);
        return ResponseEntity.ok(userRS);
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyProfile(@AuthenticationPrincipal User user) {
        service.deleteMyProfile(user);
        return ResponseEntity.noContent().build();
    }

}
