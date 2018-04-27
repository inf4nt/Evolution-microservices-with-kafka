package com.evolution.direct.message.topology.core;

import com.evolution.direct.message.base.core.share.User;
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
public class MessageStateSenderTemp {

    String key;

    String text;

    User sender;

    String recipient;
}
