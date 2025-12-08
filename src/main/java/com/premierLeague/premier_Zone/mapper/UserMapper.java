package com.premierLeague.premier_Zone.mapper;

import com.premierLeague.premier_Zone.dtos.UserDto;
import com.premierLeague.premier_Zone.entity.User;

public class UserMapper {
    public static UserDto userToDto(User user){

        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setFollowedTeam(user.getFollowedTeam());

        return dto;

    }
}
