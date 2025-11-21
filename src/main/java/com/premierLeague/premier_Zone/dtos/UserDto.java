package com.premierLeague.premier_Zone.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private  String userId;
    private String username;
    private String email;
    private String role;
}
