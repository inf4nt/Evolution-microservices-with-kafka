package com.evolution.core.command;

import com.evolution.core.base.Base;
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
public class MessageCreateCommand implements Base<String> {

    String key;

    String text;

    String sender;

    String recipient;
}
