package com.evolution.direct.message.event;

import com.evolution.direct.message.share.User;
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
public class MessageStateEvent implements MessageEvent {

    String id;

    String eventId;

    String text;

    User sender;

    User recipient;

    Date postDate;

    Date putDate;

    boolean isRead;
}
