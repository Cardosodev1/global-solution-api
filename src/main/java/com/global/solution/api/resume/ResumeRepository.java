package com.global.solution.api.resume;

import com.global.solution.api.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    @Query("SELECT r FROM Resume r LEFT JOIN FETCH r.skills WHERE r.user = :user")
    Page<Resume> findByUserWithSkills(User user, Pageable pageable);

    Optional<Resume> findByIdAndUser(Long id, User user);

}
