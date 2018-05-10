package com.evolution.user.core.global;

import com.evolution.library.core.v3.global.GlobalState;
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
public class UserGlobalState extends UserDomain implements GlobalState<String> {

    @NotEmpty
    String key;

    @NotEmpty
    String eventNumber;

    @NotEmpty
    String correlation;

    @NotEmpty
    String messageType;

    @Builder
    public UserGlobalState(String username, String password, String firstName, String lastName,
                           @NotEmpty String key, @NotEmpty String eventNumber, @NotEmpty String correlation, @NotEmpty String messageType) {
        super(username, password, firstName, lastName);
        this.key = key;
        this.eventNumber = eventNumber;
        this.correlation = correlation;
        this.messageType = messageType;
    }
}
