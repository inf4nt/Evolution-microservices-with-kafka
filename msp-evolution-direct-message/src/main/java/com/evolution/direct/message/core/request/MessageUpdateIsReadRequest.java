package com.evolution.direct.message.core.request;

import com.evolution.direct.message.core.common.MessageRequestTypes;
import com.evolution.library.core.v5.Request;
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
public class MessageUpdateIsReadRequest implements Request<String, MessageRequestTypes> {

    @NotEmpty
    String key;

    final MessageRequestTypes requestType = MessageRequestTypes.MessageUpdateIsReadRequest;
}
