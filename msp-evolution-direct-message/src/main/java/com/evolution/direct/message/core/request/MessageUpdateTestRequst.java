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
public class MessageUpdateTestRequst implements Request<String, MessageRequestTypes> {

    @NotEmpty
    String key;

    @NotEmpty
    String text;

    final MessageRequestTypes requestType = MessageRequestTypes.MessageCreateRequest;
}
