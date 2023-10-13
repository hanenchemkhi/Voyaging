package com.perscholas.voyaging.security;

import com.perscholas.voyaging.model.User;
import com.perscholas.voyaging.repository.AuthorityRepository;
import com.perscholas.voyaging.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    public AppUserDetailsService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> userByEmail = userRepository.findByEmail(email);

        if (userByEmail.isEmpty()) {
            throw new UsernameNotFoundException("Invalid credentials!");
        }
        User user = userByEmail.get();

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getAuthorities().forEach(authority ->
                grantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority())));

        log.warn("User: "+ user.getEmail()+" with authorities: "+ grantedAuthorities);


        return new AppUserPrincipal(user,grantedAuthorities);


    }
}

