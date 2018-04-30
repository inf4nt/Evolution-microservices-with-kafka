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
public class UserUpdateUsernameCommand implements IUserCommand {

    @NotEmpty
    String key;

    @NotEmpty
    String username;

    @NotEmpty
    String operationNumber;
}
