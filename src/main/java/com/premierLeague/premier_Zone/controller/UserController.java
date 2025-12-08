package com.premierLeague.premier_Zone.controller;

import com.premierLeague.premier_Zone.dtos.UserDto;
import com.premierLeague.premier_Zone.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@Tag(name="User API's", description = "current-user, follow, unfollow, all-user(only for admin), create-admin(only for admin), redis-info(only for admin)")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @GetMapping("/current-user")
    @Operation(summary = "To get the current log in user, login required")
    public UserDto getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Not authenticated");

        return userService.getById(authentication.getName());
    }
    @PostMapping("/follow")
    @Operation(summary = "To follow teams for current log in user, login required")
    public ResponseEntity<?> followTeam(@RequestParam String teamName){
        return userService.followTeam(teamName);
    }

    @DeleteMapping("/unfollow")
    @Operation(summary = "To unfollow teams for current log in user, login required")
    public  ResponseEntity<?> unfollowTeam(@RequestParam String teamName){
        return userService.UnfollowTeam(teamName);
    }

    @GetMapping("/all-user")
    @PreAuthorize("hasAnyRole('ADMIN','ADMIN-BY-GOOGLE')")
    @Operation(summary = "To get all user info , Admin login required")
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(userService.getAllUser(page,size));
    }
    @PostMapping("/create-admin")
    @PreAuthorize("hasAnyRole('ADMIN','ADMIN-BY-GOOGLE')")
    @Operation(summary = "To assign admin role  , Admin login required")
    public ResponseEntity<?> createAdmin(@RequestParam String email){
        return ResponseEntity.ok(userService.addAdmin(email));
    }



    @GetMapping("/redis-info")
    @PreAuthorize("hasAnyRole('ADMIN','ADMIN-BY-GOOGLE')")
    @Operation(summary = "To check redis connection  , Admin login required")
    public String redisInfo() {
        // Cast to Lettuce implementation
        var lettuce = (org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory) connectionFactory;

        // Get actual standalone config built by Spring Boot
        var cfg = lettuce.getStandaloneConfiguration();

        return "Redis Host: " + cfg.getHostName() + ", Port: " + cfg.getPort();
    }

}
