package com.premierLeague.premier_Zone.service;

import com.premierLeague.premier_Zone.dtos.LogInDto;
import com.premierLeague.premier_Zone.dtos.UserDto;
import com.premierLeague.premier_Zone.dtos.UserRegisterDto;
import com.premierLeague.premier_Zone.entity.User;
import com.premierLeague.premier_Zone.mapper.UserMapper;
import com.premierLeague.premier_Zone.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder ;

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

        if(user.getRole()==null || user.getRole().isBlank()) user.setRole("USER");
        User saved = userRepository.save(user);
        log.info("Registered new user with id:{}", saved.getUserId());

        return UserMapper.userDto(saved);

    }

//    public UserDto logIn (LogInDto dto){
//        User user = userRepository.findByUsernameIgnoreCase(dto.getUsername()).orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid user or password"));
//        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid user or password");
//
//        }
//        return UserMapper.userDto(user);
//    }
    @Transactional(readOnly = true)
    public UserDto getById(String userId){
        User user = userRepository.findById(userId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));

        return UserMapper.userDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto getByUsername(String username){
        Optional<User> opt = userRepository.findByUsername(username);
        return opt.map(UserMapper :: userDto).orElse(null);
    }

    @Transactional(readOnly = true)
    public Page<UserDto>listAll(Pageable pageable){
        return userRepository.findAll(pageable).map(UserMapper :: userDto);
    }

    @Transactional
    public void delete(String userId){
        if(!userRepository.existsById(userId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
        }
        userRepository.deleteById(userId);
        log.info("Deleted user id {}", userId);
    }

}
