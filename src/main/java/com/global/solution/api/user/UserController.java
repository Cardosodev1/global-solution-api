package com.global.solution.api.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<Page<UserRS>> list(@PageableDefault(size = 15, sort = {"id"}) Pageable pageable) {
        var page = repository.findAll(pageable).map(UserRS::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/me")
    public ResponseEntity<UserRS> getMyProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(new UserRS(user));
    }

    @PutMapping("/me")
    @Transactional
    public ResponseEntity<UserRS> updateMyProfile(@RequestBody @Valid UserUpdateRQ request, @AuthenticationPrincipal User user) {
        user.update(request, passwordEncoder);
        repository.save(user);
        return ResponseEntity.ok(new UserRS(user));
    }

    @DeleteMapping("/me")
    @Transactional
    public ResponseEntity<Void> deleteMyProfile(@AuthenticationPrincipal User user) {
        repository.deleteById(user.getId());
        return ResponseEntity.noContent().build();
    }

}
