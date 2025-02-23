package com.example.demoSecurity.service;

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

    @Autowired
    public UserService (UserRepo userRepo,AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
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
    public String logIn(UserModel user) {
        if(user != null) {

            UserModel userModel = userRepo.findByUsername(user.getUsername());

            if(userModel != null) {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

                if(authentication.isAuthenticated()) {
                    return (authentication.getName()+ " Logged In");
                }
            }
            return "Bad Credentials";
        }

        return "Bad Credentials";
    }
}
