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
public class MessageUpdateTextEvent implements MessageEvent {

    String id;

    String eventId;

    String text;

    Date putDate;
}
