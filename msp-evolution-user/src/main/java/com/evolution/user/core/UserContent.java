package com.evolution.user.core;

import com.evolution.library.core.v5.Content;
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
public class UserContent implements Content {

    String username;

    String password;

    String nickname;

    String firstName;

    String lastName;
}
