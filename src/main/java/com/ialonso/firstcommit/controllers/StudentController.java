package com.ialonso.firstcommit.controllers;

import com.ialonso.firstcommit.entities.Student;
import com.ialonso.firstcommit.entities.Tag;
import com.ialonso.firstcommit.services.StudentServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class StudentController {

    private final String ROOT = "/api";
    private final Logger log = LoggerFactory.getLogger(StudentController.class);
    private final StudentServiceImpl studentService;


    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(ROOT + "/students")
    @ApiOperation("Find all Students in DB")
    public ResponseEntity<List<Student>> findAll(@RequestParam(required = false) Integer remote,
                                                 @RequestParam(required = false) Boolean mobility,
                                                 @RequestParam(required = false) String country,
                                                 @RequestParam(required = false) String location) {
        return studentService.findAll(country, location, mobility, remote);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(ROOT + "/student/" + "{id}")
    @ApiOperation("Find a Student in DB by ID")
    public ResponseEntity<Student> findOneById(@PathVariable Long id) {
        ResponseEntity<Student> result = studentService.findOneById(id);
        if (result.getStatusCode().equals(HttpStatus.NOT_FOUND))
            log.warn("Student doesn't exist in DB");
        return result;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(ROOT + "/student")
    @ApiOperation("Create a new Student in DB with a JSON")
    public ResponseEntity<Student> create(@RequestBody Student student, @CurrentSecurityContext(expression="authentication?.name")
            String email) {
        ResponseEntity<Student> result = studentService.create(student, email);
        if (result.getStatusCode().equals(HttpStatus.BAD_REQUEST))
            log.warn("Trying to create a wrong User, check the data, please!");
        if (result.getStatusCode().equals(HttpStatus.CONFLICT))
            log.warn("Email or Phone Number is already used");
        return result;
    }

    @PatchMapping(ROOT + "/student/" + "{id}")
    @ApiOperation("Update a Student in DB with a JSON")
    public ResponseEntity<Student> patchUser(@PathVariable Long id, @RequestBody Map<Object, Object> fields) throws IOException {
        ResponseEntity<Student> result = studentService.patch(id, fields);
        if (result.getStatusCode().equals(HttpStatus.NOT_FOUND))
            log.warn("Student doesn't exist in DB");
        return result;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(ROOT + "/student/" + "{id}")
    @ApiOperation("Delete a Student in DB by ID")
    public ResponseEntity delete(@PathVariable Long id) {
        ResponseEntity result = studentService.delete(id);
        if (result.getStatusCode().equals(HttpStatus.NOT_FOUND))
            log.warn("Trying to delete a Student with a non existing ID");
        return result;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(ROOT + "/students/" + "restartDB")
    @ApiOperation("Delete all Students")
    public ResponseEntity deleteAll(@RequestHeader HttpHeaders headers) {
        ResponseEntity result = studentService.deleteAll();
        if (result.getStatusCode().equals(HttpStatus.NOT_FOUND))
            log.warn("The DB is already empty");
        else
            log.warn("Deleting all by request of " + headers.get("User-Agent"));
        return result;
    }

}
