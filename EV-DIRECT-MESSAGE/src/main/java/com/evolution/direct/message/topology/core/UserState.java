package com.evolution.direct.message.topology.core;


import com.evolution.direct.message.core.State;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Wither
public class UserState implements State<String, String> {

    String key;

    String eventNumber;

    String username;

    String password;

    String firstName;

    String lastName;
}
