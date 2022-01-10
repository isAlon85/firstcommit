package com.ialonso.firstcommit.controllers;

import com.ialonso.firstcommit.security.payload.MessageResponse;
import com.ialonso.firstcommit.services.CloudinaryServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/cloudinary")
@CrossOrigin
public class CloudinaryController {

    private final Logger log = LoggerFactory.getLogger(CloudinaryController.class);

    @Autowired
    CloudinaryServiceImpl cloudinaryServiceImpl;

}
