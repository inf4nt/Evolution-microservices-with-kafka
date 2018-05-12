package com.evolution.user.core;

import com.evolution.library.core.v4.Event;
import com.evolution.user.core.common.UserEventStatus;
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
public class UserEvent implements Event<String, UserRequestTypes, UserDomain, UserEventStatus> {

    @NotEmpty
    String correlation;

    @NotEmpty
    UserRequestTypes requestType;

    @NotEmpty
    String operationNumber;

    UserDomain domain;

    @NotEmpty
    UserEventStatus userEventStatus;

    @Override
    public String getKey() {
        return domain.getKey();
    }
}
