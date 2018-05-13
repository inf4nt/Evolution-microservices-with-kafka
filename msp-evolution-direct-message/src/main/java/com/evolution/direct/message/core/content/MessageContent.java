package com.evolution.direct.message.core.content;

import com.evolution.library.core.v5.Content;
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
public class MessageContent implements Content {

    String text;

    String sender;

    String recipient;

    boolean isRead;
}
