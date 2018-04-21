package com.evolution.user.core.command;

import com.evolution.user.core.base.Base;
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
public class UserCreateCommand implements Base<String> {

    String key;

    String username;

    String password;

    String firstName;

    String lastName;
}

