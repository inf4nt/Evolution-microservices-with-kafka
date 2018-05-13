package com.evolution.direct.message.core;

import com.evolution.direct.message.core.content.MessageContent;
import com.evolution.library.core.v5.State;
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
public class MessageState implements State<String, MessageContent> {

    @NotEmpty
    String key;

    @NotEmpty
    String eventNumber;

    MessageContent content;
}
