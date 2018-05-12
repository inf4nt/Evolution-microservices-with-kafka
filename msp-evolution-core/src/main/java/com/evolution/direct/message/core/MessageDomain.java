package com.evolution.direct.message.core;

import com.evolution.direct.message.core.share.User;
import com.evolution.library.core.v4.Domain;
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
    String text;

    User sender;

    User recipient;

    boolean isRead;
}
