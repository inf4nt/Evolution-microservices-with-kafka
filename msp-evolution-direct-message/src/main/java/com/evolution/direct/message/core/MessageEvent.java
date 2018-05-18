package com.evolution.direct.message.core;

import com.evolution.direct.message.core.common.MessageEventStatus;
import com.evolution.direct.message.core.common.MessageRequestTypes;
import com.evolution.direct.message.core.content.MessageContent;
import com.evolution.library.core.v5.Event;
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
public class MessageEvent implements Event<String, MessageContent, MessageRequestTypes, MessageEventStatus> {

    @NotEmpty
    String key;

    @NotEmpty
    String correlation;

    @NotEmpty
    String operationNumber;

    @NotEmpty
    MessageRequestTypes type;

    @NotEmpty
    MessageEventStatus eventStatus;

    MessageContent content;
}
