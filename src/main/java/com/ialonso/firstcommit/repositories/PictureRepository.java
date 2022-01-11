package com.ialonso.firstcommit.repositories;

import com.ialonso.firstcommit.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {

    Picture findTagByName(String name);

}
