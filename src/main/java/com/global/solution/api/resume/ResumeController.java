package com.global.solution.api.resume;

import com.global.solution.api.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService service;

    @PostMapping
    public ResponseEntity<ResumeRS> createResume(@RequestBody @Valid ResumeRQ resumeRQ,
                                                 @AuthenticationPrincipal User user) {
        ResumeRS resumeRS = service.createResume(resumeRQ, user);
        return ResponseEntity.ok(resumeRS);
    }

    @GetMapping
    public ResponseEntity<Page<ResumeRS>> listMyResumes(@PageableDefault(size = 10, sort = "title") Pageable pageable,
                                                        @AuthenticationPrincipal User user) {
        Page<ResumeRS> page = service.listMyResumes(user, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeRS> getMyResumeById(@PathVariable Long id,
                                                    @AuthenticationPrincipal User user) {
        ResumeRS resumeRS = service.getMyResumeById(id, user);
        return ResponseEntity.ok(resumeRS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResumeRS> updateMyResume(@PathVariable Long id,
                                                   @RequestBody @Valid ResumeUpdateRQ resumeUpdateRQ,
                                                   @AuthenticationPrincipal User user) {
        ResumeRS updatedResume = service.updateMyResume(id, resumeUpdateRQ, user);
        return ResponseEntity.ok(updatedResume);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMyResume(@PathVariable Long id,
                                               @AuthenticationPrincipal User user) {
        service.deleteMyResume(id, user);
        return ResponseEntity.noContent().build();
    }

}
