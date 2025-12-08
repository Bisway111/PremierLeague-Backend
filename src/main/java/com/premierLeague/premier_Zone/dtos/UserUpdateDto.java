package com.premierLeague.premier_Zone.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateDto {
    private String username;

    @Email(message = "Enter a valid email")
    private String email;
}
