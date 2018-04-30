package com.evolution.user.command.command;

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
public class UserUpdateFirstNameLastNameCommand implements IUserCommand {

    @NotEmpty
    String key;

    @NotEmpty
    String eventNumber;

    @NotEmpty
    String firstName;

    @NotEmpty
    String lastName;

    @NotEmpty
    String operationNumber;
}
