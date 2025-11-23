package com.premierLeague.premier_Zone.controller;

import com.premierLeague.premier_Zone.dtos.*;
import com.premierLeague.premier_Zone.security.JwtUtil;
import com.premierLeague.premier_Zone.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
              UserDto userDto = userService.getByUsername(logInDto.getUsername());
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUserId(),logInDto.getPassword()));

            String token = jwtUtil.generateToken(userDto.getUserId());


            return new LoginResponse(token,userDto);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid username or password");
        }

    }
    @PutMapping("/update")
    public UserDto updateProfile(@RequestBody UserDto userDto){

        return userService.updateUser(userDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser( @Valid @RequestBody DeleteDto deleteDto){
      return userService.deleteUser(deleteDto);
    }

}
