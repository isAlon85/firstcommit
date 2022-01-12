package com.ialonso.firstcommit.repositories;

import com.ialonso.firstcommit.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    Optional<Picture> findById(Long id);

    Boolean existsByCloudinaryId(String cloudinaryId);

    Picture findByCloudinaryId(String cloudinaryId);

}
