package com.evolution.direct.message.core;

import com.evolution.direct.message.core.common.MessageEventStatus;
import com.evolution.direct.message.core.common.MessageRequestTypes;
import com.evolution.library.core.v4.Event;
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
public class MessageEvent implements Event<String, MessageRequestTypes, MessageDomain, MessageEventStatus> {

    @NotEmpty
    String key;

    @NotEmpty
    String correlation;

    @NotEmpty
    String operationNumber;

    MessageEventStatus eventStatus;

    @NotEmpty
    MessageRequestTypes requestType;

    MessageDomain domain;
}
