package com.evolution.user.core;

import com.evolution.library.core.v3.Domain;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDomain implements Domain {

    String username;

    String password;

    String firstName;

    String lastName;
}
