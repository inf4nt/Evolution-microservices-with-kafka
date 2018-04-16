package com.evolution.direct.message.processor.event;

import com.evolution.direct.message.processor.share.User;
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
public class MessageDenormalizationStateEvent {

    String id;

    String eventId;

    String text;

    User sender;

    User recipient;

    Date postDate;

    Date putDate;

    boolean isRead;
}
