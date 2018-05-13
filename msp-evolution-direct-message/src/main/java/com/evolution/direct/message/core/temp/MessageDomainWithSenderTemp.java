package com.evolution.direct.message.core.temp;

import com.evolution.direct.message.core.domain.User;
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
public class MessageDomainWithSenderTemp {

    String key;

    String eventNumber;

    String text;

    boolean isRead;

    User sender;

    String recipient;
}
