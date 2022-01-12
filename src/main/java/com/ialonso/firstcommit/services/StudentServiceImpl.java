package com.ialonso.firstcommit.services;

import com.ialonso.firstcommit.entities.Student;
import com.ialonso.firstcommit.repositories.PictureRepository;
import com.ialonso.firstcommit.repositories.RoleRepository;
import com.ialonso.firstcommit.repositories.StudentRepository;
import com.ialonso.firstcommit.repositories.UserRepository;
import com.ialonso.firstcommit.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    CloudinaryServiceImpl cloudinaryServiceImpl;

    @Override
    public ResponseEntity<List<Student>> findAll(){
        if (studentRepository.count() == 0)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(studentRepository.findAll());
    }

    @Override
    public ResponseEntity<Student> findOneById(Long id){
        Optional<Student> regStudentOpt = studentRepository.findById(id);

        if (regStudentOpt.isPresent())
            return ResponseEntity.ok(regStudentOpt.get());
        else
            return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Student> create(Student student, @CurrentSecurityContext(expression="authentication?.name") String email) {
        if (student.getId() != null)
            return ResponseEntity.badRequest().build();
        if (student.getUser() != null)
            return ResponseEntity.badRequest().build();
        if (studentRepository.existsByEmail(student.getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        if (studentRepository.existsByPhone(student.getPhone()))
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        if (student.getName().length() > 48)
            return ResponseEntity.badRequest().build();
        if (student.getEmail().length() > 48)
            return ResponseEntity.badRequest().build();
        if (student.getPhone().length() > 20)
            return ResponseEntity.badRequest().build();
        student.setUser(userRepository.findByEmail(email).get());
        Student result = studentRepository.save(student);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<Student> patch(Long id, Map<Object, Object> fields) throws IOException {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Student.class, (String) key);
                assert field != null;
                field.setAccessible(true);
                ReflectionUtils.setField(field, student.get(), value);
            });
            Student result = studentRepository.save(student.get());
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity delete(Long id){
        if (!studentRepository.existsById(id))
            return ResponseEntity.notFound().build();

        studentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity deleteAll() {
        if (studentRepository.count() == 0)
            return ResponseEntity.notFound().build();

        studentRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

}

