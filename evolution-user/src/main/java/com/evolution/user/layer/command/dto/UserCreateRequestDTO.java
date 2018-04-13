package com.evolution.user.layer.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDTO {

    String username;

    String password;

    String firstName;

    String lastName;

    String nickname;
}
