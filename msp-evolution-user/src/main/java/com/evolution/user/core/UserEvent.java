package com.evolution.user.core;

import com.evolution.library.core.v5.Event;
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
public class UserEvent implements Event<String, UserContent, UserRequestTypes, UserEventStatus> {

    @NotEmpty
    String key;

    @NotEmpty
    String correlation;

    @NotEmpty
    UserRequestTypes type;

    @NotEmpty
    String operationNumber;

    @NotEmpty
    UserEventStatus eventStatus;

    UserContent content;
}
