package com.evolution.user.query.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

import javax.validation.constraints.NotEmpty;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Wither
public class UserModel {

    @NotEmpty
    String key;

    @NotEmpty
    String eventNumber;

    @NotEmpty
    String nickname;

    @NotEmpty
    String username;

    @NotEmpty
    String password;

    @NotEmpty
    String firstName;

    @NotEmpty
    String lastName;
}
