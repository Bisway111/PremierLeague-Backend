package com.premierLeague.premier_Zone.controller;

import com.premierLeague.premier_Zone.dtos.*;
import com.premierLeague.premier_Zone.security.JwtUtil;
import com.premierLeague.premier_Zone.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Auth API's", description = "user register, user login, user info update, user delete. OAuth 2 available with google, endpoint-oauth2/authorization/google")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private  JwtUtil jwtUtil;


    @PostMapping("/register")
    @SecurityRequirements
    @Operation(summary = "To register a new user, no login required")
    public UserDto register(@Valid  @RequestBody UserRegisterDto dto){

        return userService.registration(dto);
    }

    @PostMapping("/login")
    @SecurityRequirements
    @Operation(summary = "To login a user")
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
    @Operation(summary = "To update user info username and email, and for google login user only username update is available ,  login required")
    public UserDto updateProfile( @Valid @RequestBody UserUpdateDto userUpdateDto){

        return userService.updateUser(userUpdateDto);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "To delete user account , login required")
    public ResponseEntity<?> deleteUser( @Valid @RequestBody DeleteDto deleteDto){
      return userService.deleteUser(deleteDto);
    }

}
