package com.evolution.core.event;

import com.evolution.core.command.UserUpdateFirstNameLastNameCommand;
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
public class UserUpdateFirstNameLastNameEvent implements UserEvent {

    @NotEmpty
    String key;

    @NotEmpty
    String eventNumber;

    @NotEmpty
    UserUpdateFirstNameLastNameCommand command;
}
