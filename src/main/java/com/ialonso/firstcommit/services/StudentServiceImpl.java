package com.ialonso.firstcommit.services;

import com.ialonso.firstcommit.entities.Picture;
import com.ialonso.firstcommit.entities.Resume;
import com.ialonso.firstcommit.entities.Student;
import com.ialonso.firstcommit.entities.Tag;
import com.ialonso.firstcommit.repositories.*;
import com.ialonso.firstcommit.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

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
    private TagRepository tagRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private ResumeRepository resumeRepository;

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
    public ResponseEntity<List<Student>> findByRemote(Integer remote){
        if (studentRepository.findByRemote(remote).size() == 0)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(studentRepository.findByRemote(remote));
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
                if (key == "picture") {
                    LinkedHashMap map = (LinkedHashMap)value;
                    Integer idInt = (Integer) map.get("id");
                    Long idPicture = Long.valueOf(idInt);
                    Picture picture = pictureRepository.getById(idPicture);
                    student.get().setPicture(picture);
                } else if (key == "resume") {
                    LinkedHashMap map = (LinkedHashMap)value;
                    Integer idInt = (Integer) map.get("id");
                    Long idResume = Long.valueOf(idInt);
                    Resume resume = resumeRepository.getById(idResume);
                    student.get().setResume(resume);
                } else if (key == "tags") {
                    Set<Tag> set = new HashSet<>();
                    for (LinkedHashMap tag : ((ArrayList<LinkedHashMap>) value)) {
                        Integer idInt = (Integer) tag.get("id");
                        Long idTag = Long.valueOf(idInt);
                        set.add(tagRepository.getById(idTag));
                    }
                    student.get().setTags(set);
                } else {
                    ReflectionUtils.setField(field, student.get(), value);
                }
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

