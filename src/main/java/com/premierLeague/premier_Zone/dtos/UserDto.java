package com.premierLeague.premier_Zone.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserDto {
    private  String userId;
    private String username;
    private String email;
    private String role;
    private Set<String> followedTeam ;
}
