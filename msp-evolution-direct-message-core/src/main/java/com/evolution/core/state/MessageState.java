package com.evolution.core.state;

import com.evolution.library.core.State;
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
public class MessageState implements State<String, String> {

    String key;

    String eventNumber;

    String text;

    String sender;

    String recipient;
}
