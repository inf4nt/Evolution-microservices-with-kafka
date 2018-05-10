package com.evolution.core.command;

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
public class UserUpdateFirstNameLastNameCommand implements UserCommand {

    @NotEmpty
    String key;

    @NotEmpty
    String correlationId;

    @NotEmpty
    String firstName;

    @NotEmpty
    String lastName;

    @NotEmpty
    String operationNumber;
}
