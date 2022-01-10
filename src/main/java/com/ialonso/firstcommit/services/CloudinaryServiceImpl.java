package com.ialonso.firstcommit.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ialonso.firstcommit.repositories.UserRepository;
import com.ialonso.firstcommit.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.CurrentSecurityContext;
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

    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    Cloudinary cloudinary;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private Map<String, String> valuesMap = new HashMap<>();

    public CloudinaryServiceImpl(){
        valuesMap.put("cloud_name", "ialons85");
        valuesMap.put("api_key", "447823371496323");
        valuesMap.put("api_secret", "jTHbasQY_F-SouuoIFqQBTGMEqk");
        cloudinary = new Cloudinary(valuesMap);
    }

    public File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }
}
