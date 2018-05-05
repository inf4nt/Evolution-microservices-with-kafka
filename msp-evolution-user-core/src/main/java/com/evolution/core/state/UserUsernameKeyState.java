package com.evolution.core.state;


import com.evolution.library.core.Base;
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
public class UserUsernameKeyState implements Base<String> {

    @NotEmpty
    String key;

    @NotEmpty
    String username;
}
