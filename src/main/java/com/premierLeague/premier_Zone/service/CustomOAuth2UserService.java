package com.premierLeague.premier_Zone.service;

import com.premierLeague.premier_Zone.entity.User;
import com.premierLeague.premier_Zone.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class CustomOAuth2UserService extends OidcUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService EmailService;

    @Override
    @Transactional
    public OidcUser loadUser(OidcUserRequest request)throws OAuth2AuthenticationException{

        OidcUser user =super.loadUser(request);

        Map<String, Object> attrs = user.getAttributes();

        String email = (String) attrs.get("email");
        String name = (String) attrs.get("name");
        if(email==null || email.isBlank()){
            throw new OAuth2AuthenticationException("Google email not provided");
        }
        Optional<User> existing = userRepository.findByEmailIgnoreCase(email);
        if(existing.isEmpty()){
            User u = new User();
            u.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            u.setEmail(email);
            u.setUsername(name);
            u.setRole("USER-BY-GOOGLE");
            u.setDate(LocalDateTime.now());
            userRepository.save(u);
            try {
                EmailService.sendWelcomemail(u.getEmail(), u.getUsername());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }
}
