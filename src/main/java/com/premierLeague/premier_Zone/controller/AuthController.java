package com.premierLeague.premier_Zone.controller;

import com.premierLeague.premier_Zone.dtos.LogInDto;
import com.premierLeague.premier_Zone.dtos.LoginResponse;
import com.premierLeague.premier_Zone.dtos.UserDto;
import com.premierLeague.premier_Zone.dtos.UserRegisterDto;
import com.premierLeague.premier_Zone.security.JwtUtil;
import com.premierLeague.premier_Zone.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private  JwtUtil jwtUtil;


    @PostMapping("/register")
    public UserDto register(@Valid  @RequestBody UserRegisterDto dto){

        return userService.registration(dto);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LogInDto logInDto){

        try{
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logInDto.getUsername(),logInDto.getPassword()));

            String token = jwtUtil.generateToken(logInDto.getUsername());

            UserDto userDto = userService.getByUsername(logInDto.getUsername());
            return new LoginResponse(token,userDto);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid username or password");
        }

    }

}
