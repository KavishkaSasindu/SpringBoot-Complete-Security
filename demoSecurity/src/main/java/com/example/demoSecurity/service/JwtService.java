package com.example.demoSecurity.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Service
public class JwtService {

    private final SecretKey secretKey;

    public JwtService() {
        try{
            SecretKey key = KeyGenerator.getInstance("HmacSHA256").generateKey();
            secretKey = Keys.hmacShaKeyFor(key.getEncoded());
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }


    public String generateToken(String username) {

        Map<String,Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*15))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
