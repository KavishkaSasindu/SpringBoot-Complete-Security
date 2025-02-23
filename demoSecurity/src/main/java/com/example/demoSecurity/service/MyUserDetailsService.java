package com.example.demoSecurity.service;

import com.example.demoSecurity.model.UserModel;
import com.example.demoSecurity.model.UserPrincipal;
import com.example.demoSecurity.repo.UserRepo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Data
@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepo userrepo;

    @Autowired
    public MyUserDetailsService(UserRepo userrepo) {
        this.userrepo = userrepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserModel user = userrepo.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserPrincipal(user);
    }

}
