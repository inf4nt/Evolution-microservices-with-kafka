package com.evolution.user.core.request;

import com.evolution.library.core.v4.Request;
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
public class UserUpdateFirstNameRequest implements Request<String, UserRequestTypes> {

    @NotEmpty
    String key;

    @NotEmpty
    String firstName;

    final UserRequestTypes requestType = UserRequestTypes.UserUpdateFirstNameRequest;
}
