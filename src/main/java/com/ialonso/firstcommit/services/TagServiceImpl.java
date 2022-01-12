package com.ialonso.firstcommit.services;

import com.ialonso.firstcommit.entities.Tag;
import com.ialonso.firstcommit.repositories.TagRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    @Override
    public ResponseEntity<List<Tag>> findAll(){
        if (tagRepository.count() == 0)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(tagRepository.findAll());
    }

    @Override
    public ResponseEntity<Tag> findOneById(Long id){
        Optional<Tag> regStudentOpt = tagRepository.findById(id);

        if (regStudentOpt.isPresent())
            return ResponseEntity.ok(regStudentOpt.get());
        else
            return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Tag> create(Tag tag) {
        if (tag.getId() != null)
            return ResponseEntity.badRequest().build();
        if (tagRepository.existsByName(tag.getName()))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        Tag result = tagRepository.save(tag);
        return ResponseEntity.ok(result);
    }

}

