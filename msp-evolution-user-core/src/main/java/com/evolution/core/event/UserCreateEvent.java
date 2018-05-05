package com.evolution.core.event;

import com.evolution.core.command.UserCreateCommand;
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
public class UserCreateEvent implements UserEvent {

    @NotEmpty
    String key;

    @NotEmpty
    UserCreateCommand userCreateCommand;

    @NotEmpty
    String eventNumber;
}
