package com.premierLeague.premier_Zone.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.premierLeague.premier_Zone.entity.User;
import com.premierLeague.premier_Zone.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;


    public void onAuthenticationSuccess(HttpServletRequest rq, HttpServletResponse res, Authentication auth) throws IOException{
        OAuth2User oAuth2User = (OAuth2User) auth.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        Optional<User> opt = userRepository.findByEmailIgnoreCase(email);
        if(opt.isEmpty()){
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("{\"error\":\"User not found after OAuth\"}");
            return;
        }
        User user = opt.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        String token = jwtUtil.generateToken(user.getUserId());
        Map<String,Object> json = new HashMap<>();
        json.put("token",token);
        json.put("email",user.getEmail());
        json.put("userId",user.getUserId());

        res.setContentType("application/json");
        res.getWriter().write(objectMapper.writeValueAsString(json));


    }
}
