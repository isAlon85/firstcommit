package com.ialonso.firstcommit.services;

import com.ialonso.firstcommit.entities.Student;
import com.ialonso.firstcommit.entities.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface StudentService {

    ResponseEntity<List<Student>> findAll(String country, String location, Boolean mobility, Integer remote);

    ResponseEntity<Student> findOneById(Long id);

    ResponseEntity<Student> create(Student student, @CurrentSecurityContext(expression="authentication?.name") String email);

    ResponseEntity<Student> patch(Long id, Map<Object, Object> fields) throws IOException;

    ResponseEntity delete(Long id);

    ResponseEntity deleteAll();

}
