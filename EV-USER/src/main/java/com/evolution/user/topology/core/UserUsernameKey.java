package com.evolution.user.topology.core;


import com.evolution.user.core.Base;
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
public class UserUsernameKey implements Base<String> {

    @NotEmpty
    String key;

    @NotEmpty
    String username;
}
