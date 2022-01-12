package com.ialonso.firstcommit.repositories;

import com.ialonso.firstcommit.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findTagByName(String name);

    Boolean existsByName(String name);

}
