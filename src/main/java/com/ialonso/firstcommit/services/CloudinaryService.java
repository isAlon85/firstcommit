package com.ialonso.firstcommit.services;

import com.ialonso.firstcommit.entities.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


public interface CloudinaryService {

    Picture uploadPicture(MultipartFile multipartFile) throws IOException;

    boolean deletePicture(String id) throws IOException;

    File convert(MultipartFile multipartFile) throws IOException;

}