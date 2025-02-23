package com.example.demoSecurity.service;

import com.example.demoSecurity.dto.UserResponse;
import com.example.demoSecurity.model.UserModel;
import com.example.demoSecurity.repo.UserRepo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Data
@NoArgsConstructor
@Service
public class UserService {

    private UserRepo userRepo;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @Autowired
    public UserService (UserRepo userRepo,AuthenticationManager authenticationManager,JwtService jwtService) {
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

//    register user in db
    public UserModel registerUser(UserModel user) {
        if(user != null) {
            UserModel userModel = userRepo.findByUsername(user.getUsername());
            if(userModel != null) {
                return null;
            }
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return userRepo.save(user);
        }

        return null;
    }

//    login
    public UserResponse logIn(UserModel user) {
        if(user != null) {

            UserModel userModel = userRepo.findByUsername(user.getUsername());

            if(userModel != null) {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

                if(authentication.isAuthenticated()) {
                    String token = jwtService.generateToken(user.getUsername());
                    return (new UserResponse(
                            user.getId(),
                            user.getUsername(),
                            token
                    ));
                }
            }
            return null;
        }

        return null;
    }
}
