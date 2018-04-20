package com.evolution.direct.message.event.temp;

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
public class MessageDenormalizationStateSenderTemp {

    String id;

    String eventId;

    String text;

    User sender;

    String recipient;

    Date postDate;

    Date putDate;

    boolean isRead;
}