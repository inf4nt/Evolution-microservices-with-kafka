package com.evolution.core.state;

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
public class MessageState implements IMessageState {

    String key;

    String eventNumber;

    String text;

    String sender;

    String recipient;
}
