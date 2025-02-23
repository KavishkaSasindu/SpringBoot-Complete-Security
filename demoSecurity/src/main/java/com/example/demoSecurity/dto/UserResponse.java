package com.example.demoSecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    private int id;
    private String username;
    private String token;

    public UserResponse(int id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;

    }
}
