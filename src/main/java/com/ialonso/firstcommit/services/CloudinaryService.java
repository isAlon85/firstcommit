package com.ialonso.firstcommit.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


public interface CloudinaryService {

    File convert(MultipartFile multipartFile) throws IOException;

}