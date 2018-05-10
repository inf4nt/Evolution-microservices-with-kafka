package com.evolution.core.aggregator;

import com.evolution.library.core.v2.EventAggregator;
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
public class UserEventAggregator implements EventAggregator<String> {

    @NotEmpty
    String key;

    @NotEmpty
    String correlationId;

    @NotEmpty
    String commandNumber;

    @NotEmpty
    String eventType;

    @NotEmpty
    String username;

    @NotEmpty
    String password;

    @NotEmpty
    String firstName;

    @NotEmpty
    String lastName;
}
