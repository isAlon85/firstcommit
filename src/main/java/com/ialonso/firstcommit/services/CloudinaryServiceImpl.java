package com.ialonso.firstcommit.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ialonso.firstcommit.entities.Picture;
import com.ialonso.firstcommit.entities.Resume;
import com.ialonso.firstcommit.entities.Student;
import com.ialonso.firstcommit.repositories.PictureRepository;
import com.ialonso.firstcommit.repositories.ResumeRepository;
import com.ialonso.firstcommit.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class CloudinaryServiceImpl implements CloudinaryService{

    Cloudinary cloudinary;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private StudentRepository studentRepository;

    private Map<String, String> valuesMap = new HashMap<>();

    public CloudinaryServiceImpl(){
        valuesMap.put("cloud_name", "ialons85");
        valuesMap.put("api_key", "447823371496323");
        valuesMap.put("api_secret", "jTHbasQY_F-SouuoIFqQBTGMEqk");
        cloudinary = new Cloudinary(valuesMap);
    }

    public Picture uploadPicture(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);
        Picture picture = new Picture();
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        // Save frontId data from image
        result.forEach((key, value) -> {
            if (Objects.equals(key, "secure_url")) picture.setUrl((String) value);
            if (Objects.equals(key, "public_id")) picture.setCloudinaryId((String) value);
        });
        pictureRepository.save(picture);
        return picture;
    }

    public boolean deletePicture(String id) throws IOException {
        if (pictureRepository.existsByCloudinaryId(id)) {
            //Connecting picture with proper Student
            Picture picture = pictureRepository.findByCloudinaryId(id);
            Optional<Student> student = studentRepository.findByPicture(picture);
            if (student.isPresent()) {
                student.get().setPicture(null);
                studentRepository.save(student.get());
            }
            //Deleting frontId
            Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
            pictureRepository.delete(pictureRepository.findByCloudinaryId(id));
            return true;
        }
        return false;
    }

    public Resume uploadResume(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);
        Resume resume = new Resume();
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        // Save frontId data from image
        result.forEach((key, value) -> {
            if (Objects.equals(key, "secure_url")) resume.setUrl((String) value);
            if (Objects.equals(key, "public_id")) resume.setCloudinaryId((String) value);
        });
        resumeRepository.save(resume);
        return resume;
    }

    public boolean deleteResume(String id) throws IOException {
        if (resumeRepository.existsByCloudinaryId(id)) {
            //Connecting picture with proper Student
            Resume resume = resumeRepository.findByCloudinaryId(id);
            Optional<Student> student = studentRepository.findByResume(resume);
            if (student.isPresent()) {
                student.get().setResume(null);
                studentRepository.save(student.get());
            }
            //Deleting frontId
            Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
            resumeRepository.delete(resumeRepository.findByCloudinaryId(id));
            return true;
        }
        return false;
    }

    public File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }
}
