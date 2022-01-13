package com.ialonso.firstcommit.repositories;


import com.ialonso.firstcommit.entities.Picture;
import com.ialonso.firstcommit.entities.Resume;
import com.ialonso.firstcommit.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Optional<Student> findByName(String name);

    Boolean existsByName(String name);

    Optional<Student> findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<Student> findByPhone(String phone);

    Boolean existsByPhone(String phone);

    List<Student> findByRemote(Integer remote);

    Optional<Student> findByPicture(Picture picture);

    Boolean existsByPicture(Picture picture);

    Optional<Student> findByResume(Resume resume);

    Boolean existsByResume(Resume resume);

}