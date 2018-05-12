package com.evolution.user.core;

import com.evolution.library.core.v4.StateEvent;
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
public class UserStateEvent implements StateEvent<String, UserRequestTypes, UserDomain> {

    @NotEmpty
    String correlation;

    @NotEmpty
    UserRequestTypes requestType;

    @NotEmpty
    String operationNumber;

    UserDomain domain;

    @Override
    public String getKey() {
        return domain.getKey();
    }
}
