package com.evolution.user.core.global;

import com.evolution.library.core.v3.global.GlobalCommand;
import com.evolution.user.core.UserDomain;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserGlobalCommand extends UserDomain implements GlobalCommand<String> {

    @NotEmpty
    String key;

    @NotEmpty
    String commandNumber;

    @NotEmpty
    String correlation;

    @NotEmpty
    String messageType;

    @Builder
    public UserGlobalCommand(String username, String password, String firstName, String lastName,
                             @NotEmpty String key, @NotEmpty String commandNumber, @NotEmpty String correlation, @NotEmpty String messageType) {
        super(username, password, firstName, lastName);
        this.key = key;
        this.commandNumber = commandNumber;
        this.correlation = correlation;
        this.messageType = messageType;
    }
}
