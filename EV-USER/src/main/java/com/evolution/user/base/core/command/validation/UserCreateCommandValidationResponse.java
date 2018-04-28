package com.evolution.user.base.core.command.validation;

import com.evolution.user.base.core.command.UserCreateCommand;
import com.evolution.user.base.core.common.CommandErrors;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Wither
public class UserCreateCommandValidationResponse implements CommandValidationResponse {

    @NotEmpty
    String key;

    @NotEmpty
    UserCreateCommand userCreateCommand;

    @NotNull
    List<CommandErrors> errors;

    @NotEmpty
    String operationNumber;
}
