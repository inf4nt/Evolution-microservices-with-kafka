package com.evolution.user.base.core.command;

import com.evolution.user.base.core.common.CommandErrors;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

import java.util.ArrayList;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Wither
public class UserCreateValidateCommand implements IUserCommand {

    String key;

    String username;

    String password;

    String firstName;

    String lastName;

    List<CommandErrors> errorsList = new ArrayList<>();
}
