package com.evolution.direct.message.core;

import com.evolution.direct.message.core.common.MessageRequestTypes;
import com.evolution.library.core.v4.StateEvent;
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
public class MessageEventState implements StateEvent<String, MessageRequestTypes, MessageDomain> {

    @NotEmpty
    String correlation;

    @NotEmpty
    MessageRequestTypes requestType;

    @NotEmpty
    String operationNumber;

    MessageDomain domain;

    @Override
    public String getKey() {
        return domain.getKey();
    }
}
