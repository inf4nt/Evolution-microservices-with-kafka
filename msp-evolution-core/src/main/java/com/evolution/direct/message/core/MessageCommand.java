package com.evolution.direct.message.core;

import com.evolution.direct.message.core.common.MessageRequestTypes;
import com.evolution.library.core.v4.Command;
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
public class MessageCommand implements Command<String, MessageRequestTypes, MessageDomain> {

    @NotEmpty
    String key;

    @NotEmpty
    String correlation;

    @NotEmpty
    String operationNumber;

    @NotEmpty
    MessageRequestTypes requestType;

    MessageDomain domain;

    public String getKey() {
        return this.domain.getKey();
    }
}
