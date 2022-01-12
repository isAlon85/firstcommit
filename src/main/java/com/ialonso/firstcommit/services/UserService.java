package com.ialonso.firstcommit.services;

import com.ialonso.firstcommit.entities.User;
import com.ialonso.firstcommit.security.payload.JwtResponse;
import com.ialonso.firstcommit.security.payload.LoginRequest;
import com.ialonso.firstcommit.security.payload.MessageResponse;
import com.ialonso.firstcommit.security.payload.RegisterRequest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseEntity<List<User>> findAll();

    ResponseEntity<User> findOneById(Long id);

    ResponseEntity<MessageResponse> register(RegisterRequest request);

    ResponseEntity<JwtResponse> login(LoginRequest request);

    ResponseEntity<User> patch(Long id, Map<Object, Object> fields) throws IOException;

    ResponseEntity delete(Long id);

    ResponseEntity deleteAll();

    ResponseEntity<User> whoami (String username);

}
