package com.global.solution.api.analysis;

import com.global.solution.api.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JobAnalysisRepository extends JpaRepository<JobAnalysis, Long> {

    @Query("SELECT ja FROM JobAnalysis ja WHERE ja.user = :user")
    Page<JobAnalysis> findByUser(User user, Pageable pageable);

    Optional<JobAnalysis> findByIdAndUser(Long id, User user);

    @Query("SELECT ja FROM JobAnalysis ja " +
           "LEFT JOIN FETCH ja.analysisResults ar " +
           "LEFT JOIN FETCH ar.skill " +
           "WHERE ja.id = :id AND ja.user = :user")
    Optional<JobAnalysis> findByIdAndUserWithResults(Long id, User user);

}
