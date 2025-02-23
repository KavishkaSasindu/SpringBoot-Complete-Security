package com.example.demoSecurity.controller;

import com.example.demoSecurity.dto.UserResponse;
import com.example.demoSecurity.model.UserModel;
import com.example.demoSecurity.service.JwtService;
import com.example.demoSecurity.service.UserService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Data
@NoArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private JwtService jwtService;

    @Autowired
    public UserController(UserService userService,JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserModel user) {
        try{
            if(user != null) {
                return ResponseEntity.status(HttpStatus.OK).body(userService.registerUser(user));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or password is incorrect");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//    login user
    @PostMapping("/login")
    public ResponseEntity<?> logInUser(@RequestBody UserModel user) {

        try{
            if(user != null) {

                UserResponse  userResponse = userService.logIn(user);

                if(userResponse != null) {
                    return ResponseEntity.status(HttpStatus.OK).body(userResponse);
                }

            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or password is incorrect");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//    test controller
    @GetMapping("/")
    public ResponseEntity<?> testController() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello World User");
    }
}
