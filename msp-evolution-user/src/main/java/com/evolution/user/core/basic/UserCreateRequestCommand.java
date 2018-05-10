package com.evolution.user.core.basic;

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
public class UserCreateRequestCommand {

    String key;

    String username;

    String password;

    String lastName;

    String firstName;
}
