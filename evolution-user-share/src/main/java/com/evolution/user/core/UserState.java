package com.evolution.user.core;

import com.evolution.library.core.v5.State;
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
public class UserState implements State<String, UserContent> {

    @NotEmpty
    String key;

    @NotEmpty
    String operationNumber;

    UserContent content;
}
