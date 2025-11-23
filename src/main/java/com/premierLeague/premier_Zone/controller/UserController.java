package com.premierLeague.premier_Zone.controller;

import com.premierLeague.premier_Zone.dtos.UserDto;
import com.premierLeague.premier_Zone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/current-user")
    public UserDto getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Not authenticated");

        return userService.getById(authentication.getName());
    }
    @PostMapping("/follow")
    public ResponseEntity<?> followTeam(@RequestParam String teamName){
        return userService.followTeam(teamName);
    }
    @DeleteMapping("/unfollow")
    public  ResponseEntity<?> unfollowTeam(@RequestParam String teamName){
        return userService.UnfollowTeam(teamName);
    }
}
