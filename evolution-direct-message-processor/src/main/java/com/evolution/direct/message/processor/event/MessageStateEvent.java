package com.evolution.direct.message.processor.event;

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
public class MessageStateEvent {

    String id;

    String eventId;

    String text;

    String sender;

    String recipient;

    Date postDate;

    Date putDate;

    boolean isRead;
}
