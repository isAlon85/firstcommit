package com.ialonso.firstcommit.repositories;

import com.ialonso.firstcommit.entities.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    Optional<Resume> findById(Long id);

    Boolean existsByCloudinaryId(String cloudinaryId);

    Resume findByCloudinaryId(String cloudinaryId);

}
