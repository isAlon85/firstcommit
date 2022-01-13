package com.ialonso.firstcommit.controllers;

import com.ialonso.firstcommit.entities.Picture;
import com.ialonso.firstcommit.entities.Resume;
import com.ialonso.firstcommit.security.payload.MessageResponse;
import com.ialonso.firstcommit.services.CloudinaryServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin
public class CloudinaryController {

    private final String ROOT = "/api";
    private final Logger log = LoggerFactory.getLogger(CloudinaryController.class);

    @Autowired
    CloudinaryServiceImpl cloudinaryServiceImpl;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(ROOT + "/picture")
    @ApiOperation("Upload picture")
    public Picture uploadPicture (@RequestParam MultipartFile multipartFile) throws IOException {
        Picture result = cloudinaryServiceImpl.uploadPicture(multipartFile);
        return result;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(ROOT + "/picture/{id}")
    @ApiOperation("Delete picture")
    public ResponseEntity<Map> deletePicture (@PathVariable("id") String id) throws IOException {
        boolean result = cloudinaryServiceImpl.deletePicture(id);
        if (result) {
            log.info("Picture deleted successful");
            return new ResponseEntity(new MessageResponse("Picture deleted successful"), HttpStatus.OK);
        } else {
            log.error("Picture doesn't exist");
            return new ResponseEntity(new MessageResponse("Picture doesn't exist"), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(ROOT + "/resume")
    @ApiOperation("Upload resume")
    public Resume uploadResume (@RequestParam MultipartFile multipartFile) throws IOException {
        Resume result = cloudinaryServiceImpl.uploadResume(multipartFile);
        return result;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(ROOT + "/resume/{id}")
    @ApiOperation("Delete resume")
    public ResponseEntity<Map> deleteResume (@PathVariable("id") String id) throws IOException {
        boolean result = cloudinaryServiceImpl.deleteResume(id);
        if (result) {
            log.info("Resume deleted successful");
            return new ResponseEntity(new MessageResponse("Resume deleted successful"), HttpStatus.OK);
        } else {
            log.error("Resume doesn't exist");
            return new ResponseEntity(new MessageResponse("Resume doesn't exist"), HttpStatus.BAD_REQUEST);
        }
    }

}
