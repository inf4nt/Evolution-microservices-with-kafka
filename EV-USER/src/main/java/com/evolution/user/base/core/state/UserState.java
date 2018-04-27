package com.evolution.user.base.core.state;


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
public class UserState implements IUserState {

    String key;

    String eventNumber;

    String username;

    String password;

    String firstName;

    String lastName;
}
