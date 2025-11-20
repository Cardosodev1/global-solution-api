package com.global.solution.api.analysis;

import com.global.solution.api.analysis.rqrs.JobAnalysisListRS;
import com.global.solution.api.analysis.rqrs.JobAnalysisRQ;
import com.global.solution.api.analysis.rqrs.JobAnalysisRS;
import com.global.solution.api.analysis.rqrs.JobAnalysisStatusRS;
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
@RequestMapping("/analysis")
@RequiredArgsConstructor
public class JobAnalysisController {

    private final JobAnalysisService service;

    @PostMapping
    public ResponseEntity<JobAnalysisStatusRS> createJobAnalysis(@RequestBody @Valid JobAnalysisRQ jobAnalysisRQ,
                                                           @AuthenticationPrincipal User user) {
        JobAnalysisStatusRS analysisStatusRS = service.createJobAnalysis(jobAnalysisRQ, user);
        return ResponseEntity.ok(analysisStatusRS);
    }

    @GetMapping
    public ResponseEntity<Page<JobAnalysisListRS>> listMyAnalyses(@PageableDefault(size = 10, sort = "createdAt") Pageable pageable,
                                                                  @AuthenticationPrincipal User user) {
        Page<JobAnalysisListRS> page = service.listMyAnalyses(user, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobAnalysisRS> getMyAnalysisById(@PathVariable Long id,
                                                           @AuthenticationPrincipal User user) {
        JobAnalysisRS analysisRS = service.getMyAnalysisById(id, user);
        return ResponseEntity.ok(analysisRS);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMyAnalysis(@PathVariable Long id,
                                                 @AuthenticationPrincipal User user) {
        service.deleteMyAnalysis(id, user);
        return ResponseEntity.noContent().build();
    }

}