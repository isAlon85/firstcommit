package com.ialonso.firstcommit.services;

import com.ialonso.firstcommit.entities.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface StudentService {

    ResponseEntity<List<Student>> findAll();

    ResponseEntity<Student> findOneById(Long id);

    ResponseEntity<List<Student>> findByRemote(Integer remote);

    ResponseEntity<Student> create(Student student, @CurrentSecurityContext(expression="authentication?.name") String email);

    ResponseEntity<Student> patch(Long id, Map<Object, Object> fields) throws IOException;

    ResponseEntity delete(Long id);

    ResponseEntity deleteAll();

}
