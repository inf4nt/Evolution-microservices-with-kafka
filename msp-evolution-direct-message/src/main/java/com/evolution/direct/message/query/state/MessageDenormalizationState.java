package com.evolution.direct.message.query.state;

import com.evolution.direct.message.share.User;
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
