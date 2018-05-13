package com.evolution.direct.message.core.domain;

import com.evolution.library.core.v5.Domain;
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
public class MessageDomain implements Domain<String> {

    @NotEmpty
    String key;

    @NotEmpty
    String eventNumber;

    @NotEmpty
    String text;

    @NotEmpty
    User sender;

    @NotEmpty
    User recipient;

    @NotEmpty
    boolean isRead;
}
