package com.premierLeague.premier_Zone.service;

import com.premierLeague.premier_Zone.dtos.*;
import com.premierLeague.premier_Zone.entity.User;
import com.premierLeague.premier_Zone.mapper.UserMapper;
import com.premierLeague.premier_Zone.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder ;

    @Autowired
    private EmailService EmailService;

    @Transactional
    public UserDto registration(UserRegisterDto userRegisterDto){

        //Checking the nulls
      if(userRegisterDto == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Registration data is required");

      String email = userRegisterDto.getEmail();
      if(email==null || email.isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email is required");

      String rawPassword = userRegisterDto.getPassword();
      if(rawPassword==null || rawPassword.length()<6) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password must be at least 6 characters");

      //Checking the duplicates
        if(userRepository.existsByEmailIgnoreCase(email)) throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already taken");

        //Store in the DB
        User user = new User();
        user.setUsername(userRegisterDto.getUsername());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setDate(LocalDateTime.now());

        if(user.getRole()==null || user.getRole().isBlank()) user.setRole("USER");
        User saved = userRepository.save(user);
        log.info("Successfully registered new user with ID: {}", saved.getUserId());
        try {
            EmailService.sendWelcomemail(user.getEmail(), user.getUsername());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return UserMapper.userToDto(saved);
    }

    @Transactional(readOnly = true)
    public Page<UserDto> getAllUser(int page, int size){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()) {
            log.warn("Fetch all user: not authenticated");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Not authenticated");
        }

        String AdminId = authentication.getName();
        Optional<User> adminOpt = userRepository.findById(AdminId);
        User adminUser = adminOpt.orElseThrow(()->{
            log.warn("Fetch all user: Admin not found");
            return new ResponseStatusException(HttpStatus.NOT_FOUND,"Admin not found");
        });

        if(!"ADMIN-BY-GOOGLE".equals(adminUser.getRole()) && !"ADMIN".equals(adminUser.getRole())){
            log.warn("Fetch all user: You are not a Admin");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Not Admin");
        }

        Pageable pageable= PageRequest.of(page,size);
       Page<UserDto> result =userRepository.findAll(pageable).map(UserMapper::userToDto);
        log.info("Fetched {} users",result.getTotalElements());
       return result;
    }
    @Transactional
    public ResponseEntity<?> addAdmin(String email){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()) {
            log.warn("Admin creation failed: not authenticated");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Not authenticated");
        }

        String AdminId = authentication.getName();
        Optional<User> adminOpt = userRepository.findById(AdminId);
        User adminUser = adminOpt.orElseThrow(()->{
            log.warn("Admin creation failed: Admin not found");
            return new ResponseStatusException(HttpStatus.NOT_FOUND,"Admin not found");
        });

        if(!"ADMIN-BY-GOOGLE".equals(adminUser.getRole()) && !"ADMIN".equals(adminUser.getRole())){
            log.warn("Admin creation failed: You are not a Admin");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Not Admin");
        }

        Optional<User> opt = userRepository.findByEmailIgnoreCase(email);
        User user = opt.orElseThrow(()->{
            log.warn("Admin creation failed: user not found");
            return new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        });

        if("ADMIN-BY-GOOGLE".equals(user.getRole()) || "ADMIN".equals(user.getRole())){
            return ResponseEntity.ok(new APIMessages(email+" already a Admin"));
        }else if("USER-BY-GOOGLE".equals(user.getRole())){
            user.setRole("ADMIN-BY-GOOGLE");
        }else{
            user.setRole("ADMIN");
        }
        log.info("User {} is now {}",user.getEmail(),user.getRole());
        userRepository.save(user);
        return ResponseEntity.ok(new APIMessages(email+" now  Admin"));
    }

    @Transactional(readOnly = true)
    public UserDto getById(String userId){
        log.info("Fetching user by ID: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        log.info("Fetched user by ID: {}", userId);
        return UserMapper.userToDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto getByUsername(String username){
        log.info("Fetching user by username: {}", username);
        Optional<User> opt = userRepository.findByUsername(username);
        if(!opt.isEmpty())log.info("Fetching user by username: {}", username);
        else log.warn("User not found by username: {}", username);
        return opt.map(UserMapper ::userToDto).orElse(null);
    }



   @Transactional
   public ResponseEntity<?> followTeam(String teamName){
        log.info("Request to follow team: {}", teamName);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()) {
            log.warn("Follow team failed: not authenticated");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Not authenticated");
        }

        Optional<User> opt = userRepository.findById(authentication.getName());
        User user = opt.orElseThrow(()->{
            log.warn("Follow failed: current user not found");
            return new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");});


        if(user.getFollowedTeam().contains(teamName)){
            log.warn("User {} already following team '{}'", user.getUserId(), teamName);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Already following this team");
        }
        user.getFollowedTeam().add(teamName);
        userRepository.save(user);
        log.info("User {} started following team '{}'", user.getUserId(), teamName);
    return  ResponseEntity.ok(new APIMessages("Following "+teamName));
    }

    @Transactional
    public ResponseEntity<?> UnfollowTeam(String teamName){
        log.info("Request to unfollow team: {}", teamName);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            log.warn("Unfollow failed: not authenticated");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Not authenticated");}

        Optional<User> opt = userRepository.findById(authentication.getName());
        User user = opt.orElseThrow(()->{
            log.warn("Unfollow failed: not authenticated");
            return new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");});
        if(!user.getFollowedTeam().contains(teamName)){
            log.warn("User {} not following team '{}'", user.getUserId(), teamName);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "User not following this team");
        }
        user.getFollowedTeam().remove(teamName);
        userRepository.save(user);
        log.info("User {} unfollowed team '{}'", user.getUserId(), teamName);
        return ResponseEntity.ok(new APIMessages("Unfollowing "+teamName));
    }

    @Transactional
    public UserDto updateUser(UserUpdateDto userUpdateDto){
        log.info("Updating user profile...");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()) {
            log.warn("User update failed: not authenticated");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Not authenticated");}

        Optional<User> opt = userRepository.findById(authentication.getName());
        User user = opt.orElseThrow(()->{
            log.warn("User update failed: user not found");
            return new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        });
           if( !"USER-BY-GOOGLE".equals(user.getRole()) && !"ADMIN-BY-GOOGLE".equals(user.getRole())) {
               user.setUsername(userUpdateDto.getUsername());
               user.setEmail(userUpdateDto.getEmail());
           }else{
               user.setUsername(userUpdateDto.getUsername());
           }

        userRepository.save(user);
        log.info("User {} updated successfully", user.getUserId());
        return UserMapper.userToDto(user);
    }

    @Transactional
    public ResponseEntity<?> deleteUser(DeleteDto deleteDto){
        log.info("User requested account deletion");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            log.warn("Delete failed: not authenticated");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Not authenticated");
        }

        Optional<User> opt = userRepository.findById(authentication.getName());
        User user = opt.orElseThrow(()->{
            log.warn("Delete failed: user not found");
            return new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");});
        if("USER-BY-GOOGLE".equals(user.getRole()) || "ADMIN-BY-GOOGLE".equals(user.getRole())){
            userRepository.deleteById(user.getUserId());
            log.info("User {} deleted their account", user.getUserId());
            return ResponseEntity.ok(new APIMessages("Account deleted successfully"));
        }
        if(!passwordEncoder.matches(deleteDto.getPassword(), user.getPassword())){
            log.warn("Delete failed: incorrect password for user {}", user.getUserId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password did not match");
        }else {
            userRepository.deleteById(user.getUserId());
            log.info("User {} deleted their account", user.getUserId());
        }
      return ResponseEntity.ok(new APIMessages("Account deleted successfully"));
    }

}
