package com.premierLeague.premier_Zone.mapper;

import com.premierLeague.premier_Zone.dtos.UserDto;
import com.premierLeague.premier_Zone.entity.User;

public class UserMapper {
    public static UserDto userDto(User user){

        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());

        return dto;

    }
}
