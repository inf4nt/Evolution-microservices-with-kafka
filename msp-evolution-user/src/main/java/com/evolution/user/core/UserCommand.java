package com.evolution.user.core;

import com.evolution.library.core.v4.Command;
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
public class UserCommand implements Command<String, UserRequestTypes, UserDomain> {

    @NotEmpty
    String correlation;

    @NotEmpty
    String operationNumber;

    @NotEmpty
    UserRequestTypes requestType;

    UserDomain domain;

    @Override
    public String getKey() {
        return this.domain.getKey();
    }
}
