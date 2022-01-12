package com.ialonso.firstcommit.services;

import com.ialonso.firstcommit.entities.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TagService {

    ResponseEntity<List<Tag>> findAll();

    ResponseEntity<Tag> findOneById(Long id);

    ResponseEntity<Tag> create(Tag tag);

}
