package com.ialonso.firstcommit.repositories;

import com.ialonso.firstcommit.entities.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {

    Resume findTagByName(String name);

}
