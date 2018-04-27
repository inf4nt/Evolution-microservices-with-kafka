package com.evolution.direct.message.base.core.state;

import com.evolution.direct.message.base.core.share.User;
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
public class MessageDenormalizationState implements IMessageState {

    String key;

    String eventNumber;

    String text;

    User sender;

    User recipient;
}
