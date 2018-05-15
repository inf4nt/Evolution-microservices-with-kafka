package com.evolution.user.core;

import com.evolution.library.core.v5.Domain;
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
public class UserDomain implements Domain<String> {

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
