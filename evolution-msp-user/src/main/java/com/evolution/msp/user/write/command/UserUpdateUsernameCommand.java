package com.evolution.msp.user.write.command;

import com.evolution.msp.user.core.BaseFeed;
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
public class UserUpdateUsernameCommand implements BaseFeed<String> {

    String key;

    String username;
}
