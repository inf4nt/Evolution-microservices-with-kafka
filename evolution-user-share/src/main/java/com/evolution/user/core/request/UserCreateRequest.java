package com.evolution.user.core.request;

import com.evolution.library.core.v5.Request;
import com.evolution.user.core.common.UserRequestTypes;
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
public class UserCreateRequest implements Request<String, UserRequestTypes> {

    String key;

    @NotEmpty
    String username;

    @NotEmpty
    String password;

    String firstName;

    String lastName;

    @NotEmpty
    String nickname;

    final UserRequestTypes requestType = UserRequestTypes.UserCreateRequest;
}
