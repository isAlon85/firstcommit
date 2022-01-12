package com.ialonso.firstcommit.controllers;

import com.ialonso.firstcommit.entities.Tag;
import com.ialonso.firstcommit.services.TagServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController {

    private final String ROOT = "/api";
    private final Logger log = LoggerFactory.getLogger(TagController.class);
    private final TagServiceImpl tagService;


    public TagController (TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(ROOT + "/tags")
    @ApiOperation("Find all Tags in DB")
    public ResponseEntity<List<Tag>> findAll() {
        return tagService.findAll();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(ROOT + "/tag/" + "{id}")
    @ApiOperation("Find a Tag in DB by ID")
    public ResponseEntity<Tag> findOneById(@PathVariable Long id) {
        ResponseEntity<Tag> result = tagService.findOneById(id);
        if (result.getStatusCode().equals(HttpStatus.NOT_FOUND))
            log.warn("Tag doesn't exist in DB");
        return result;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(ROOT + "/tag")
    @ApiOperation("Create a new Tag in DB with a JSON")
    public ResponseEntity<Tag> create(Tag tag) {
        ResponseEntity<Tag> result = tagService.create(tag);
        if (result.getStatusCode().equals(HttpStatus.BAD_REQUEST))
            log.warn("Trying to create a wrong Tag, check the data, please!");
        if (result.getStatusCode().equals(HttpStatus.CONFLICT))
            log.warn("Tag is already created");
        return result;
    }

}
