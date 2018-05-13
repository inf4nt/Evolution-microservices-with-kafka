package com.evolution.direct.message.core;

import com.evolution.direct.message.core.common.MessageRequestTypes;
import com.evolution.direct.message.core.content.MessageContent;
import com.evolution.library.core.v5.Command;
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
public class MessageCommand implements Command<String, MessageContent, MessageRequestTypes> {

    @NotEmpty
    String key;

    @NotEmpty
    String correlation;

    @NotEmpty
    MessageRequestTypes type;

    MessageContent content;
}
