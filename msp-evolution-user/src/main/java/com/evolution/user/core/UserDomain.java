package com.evolution.user.core;

import com.evolution.library.core.v4.Domain;
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
public class UserDomain implements Domain<String> {

    String key;

    String username;

    String password;

    String firstName;

    String lastName;
}
