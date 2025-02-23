package com.example.demoSecurity.filter;

import com.example.demoSecurity.repo.UserRepo;
import com.example.demoSecurity.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserRepo userRepo;
    private final JwtService jwtService;

    @Autowired
    public JwtFilter(UserRepo userRepo, JwtService jwtService) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if(authorization != null) {
            if(authorization.startsWith("Bearer ")) {
                String jwt_token = authorization.split("Bearer ")[1];

                if(jwt_token != null) {
                    String username = jwtService.getUsername(jwt_token);
                    System.out.println(username);
                }else {
                    filterChain.doFilter(request, response);
                }
                System.out.println(jwt_token);
            }else{
                filterChain.doFilter(request,response);
            }
        }else{
            filterChain.doFilter(request,response);
        }

    }
}
