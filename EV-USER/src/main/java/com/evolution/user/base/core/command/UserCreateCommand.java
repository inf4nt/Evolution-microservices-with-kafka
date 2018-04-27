package com.evolution.user.base.core.command;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Wither
public class UserCreateCommand implements IUserCommand {

    String key;

    String username;

    String password;

    String firstName;

    String lastName;
}

