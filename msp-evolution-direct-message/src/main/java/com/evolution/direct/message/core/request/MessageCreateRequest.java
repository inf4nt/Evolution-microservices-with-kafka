package com.evolution.direct.message.core.request;

import com.evolution.direct.message.core.common.MessageRequestTypes;
import com.evolution.library.core.v4.Request;
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
public class MessageCreateRequest implements Request<String, MessageRequestTypes> {

    String key;

    @NotEmpty
    String text;

    @NotEmpty
    String sender;

    @NotEmpty
    String recipient;

    final MessageRequestTypes requestType = MessageRequestTypes.MessageCreateRequest;
}
