package com.evolution.user.event;

import com.evolution.user.kafka.core.Event;
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
public class UserUpdateEvent implements Event<String> {

    String id;

    String eventId;

//    String username;
//
//    String password;

    String firstName;

    String lastName;

    String nickname;

    Date datePut;

//    Date postDate;
}
