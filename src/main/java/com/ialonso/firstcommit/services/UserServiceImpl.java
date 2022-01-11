package com.ialonso.firstcommit.services;

import com.ialonso.firstcommit.entities.Role;
import com.ialonso.firstcommit.entities.User;
import com.ialonso.firstcommit.repositories.RoleRepository;
import com.ialonso.firstcommit.repositories.UserRepository;
import com.ialonso.firstcommit.security.jwt.JwtTokenUtil;
import com.ialonso.firstcommit.security.payload.JwtResponse;
import com.ialonso.firstcommit.security.payload.LoginRequest;
import com.ialonso.firstcommit.security.payload.MessageResponse;
import com.ialonso.firstcommit.security.payload.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenUtil jwtTokenUtil;

    public UserServiceImpl(AuthenticationManager authManager,
                           UserRepository userRepository,
                           PasswordEncoder encoder,
                           JwtTokenUtil jwtTokenUtil){
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    CloudinaryServiceImpl cloudinaryServiceImpl;

    @Override
    public ResponseEntity<List<User>> findAll(){
        if (userRepository.count() == 0)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(userRepository.findAll());
    }

    @Override
    public ResponseEntity<User> findOneById(Long id){
        Optional<User> regUserOpt = userRepository.findById(id);

        if (regUserOpt.isPresent())
            return ResponseEntity.ok(regUserOpt.get());
        else
            return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest signUpRequest) {

        // Check 2: email
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        if (signUpRequest.getEmail().contains("@")) {
            // Create new user's account
            User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail());

            Role role = roleService.findByName("USER");
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);

            if (user.getEmail().split("@")[1].equals("ob.com")) {
                role = roleService.findByName("ADMIN");
                roleSet.add(role);
            }
            user.setRoles(roleSet);
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } else return ResponseEntity.badRequest().body(new MessageResponse("Email provided isn't a proper email address"));
    }

    @Override
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateToken(authentication);
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        return ResponseEntity.ok(new JwtResponse(jwt, user.get().getRoles()));
    }

    @Override
    public ResponseEntity<User> patch(Long id, Map<Object, Object> fields) throws IOException {
        Optional<User> user = userRepository.findById(id);
        String password = userRepository.findById(id).get().getPassword();
        String email = userRepository.findById(id).get().getEmail();
        if (user.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(User.class, (String) key);
                assert field != null;
                field.setAccessible(true);
                ReflectionUtils.setField(field, user.get(), value);
            });

            // Control for data
            if (!Objects.equals(user.get().getPassword(), password)) {
                user.get().setPassword(encoder.encode(user.get().getPassword()));
            }
            if (!user.get().getEmail().contains("@")) {
                user.get().setEmail(email);
                user.get().setUsername(email);
                return ResponseEntity.badRequest().build();
            }
            user.get().setUsername(user.get().getEmail());
            Set<Role> roleUser =new HashSet<>();
            roleUser.add(roleRepository.findRoleByName("USER"));
            user.get().setRoles(roleUser);
            // Save user to Repository
            User result = userRepository.save(user.get());
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity delete(Long id){

        if (!userRepository.existsById(id))
            return ResponseEntity.notFound().build();

        userRepository.findById(id).get().setRoles(null);
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity deleteAll() {

        if (userRepository.count() == 0)
            return ResponseEntity.notFound().build();


        for (long i = 0; i < userRepository.count(); i++){
            userRepository.findAll().iterator().next().setRoles(null);
        }
        userRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<User> whoami(String username){
        Optional<User> regUserOpt = userRepository.findByUsername(username);

        if (regUserOpt.isPresent())
            return ResponseEntity.ok(regUserOpt.get());
        else
            return ResponseEntity.notFound().build();
    }

}