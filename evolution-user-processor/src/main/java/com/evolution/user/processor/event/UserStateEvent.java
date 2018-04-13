package com.evolution.user.processor.event;

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
public class UserStateEvent implements UserEvent {

    String id;

    String eventId;

    String username;

    String password;

    String firstName;

    String lastName;

    String nickname;

    Date datePost;

    Date datePut;
}
