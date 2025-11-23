package com.premierLeague.premier_Zone.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteDto {
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
