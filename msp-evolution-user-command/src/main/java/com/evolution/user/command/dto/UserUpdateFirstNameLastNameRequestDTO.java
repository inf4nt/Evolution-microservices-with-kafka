package com.evolution.user.command.dto;

import com.evolution.library.core.RequestDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateFirstNameLastNameRequestDTO implements RequestDTO {

    @NotEmpty
    String key;

    @NotEmpty
    String firstName;

    @NotEmpty
    String lastName;
}
