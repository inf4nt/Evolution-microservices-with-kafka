package com.evolution.user.core;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserState extends UserDomain {

    @Builder
    private UserState(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
    }
}
