package com.evolution.direct.message.core;

import com.evolution.direct.message.core.share.User;
import com.evolution.library.core.v4.Domain;
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
public class MessageDomain implements Domain<String> {

    String key;

    String text;

    User sender;

    User recipient;

    boolean isRead;
}
