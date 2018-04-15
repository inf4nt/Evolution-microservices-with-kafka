package com.evolution.direct.message.event;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

import java.util.Date;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Wither
public class MessageTempStateEvent {

    String id;

    String eventId;

    String supperId;

    String text;

    String sender;

    String recipient;

    Date postDate;

    Date putDate;

    boolean isRead;
}
