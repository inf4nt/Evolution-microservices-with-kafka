package com.evolution.direct.message.core.domain;

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
public class User {

    @NotEmpty
    String key;

    @NotEmpty
    String firstName;

    @NotEmpty
    String lastName;
}
