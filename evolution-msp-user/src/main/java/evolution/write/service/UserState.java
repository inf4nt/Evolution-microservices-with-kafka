package evolution.write.service;

import evolution.core.BaseFeed;
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
public class UserState implements BaseFeed<String> {

    String key;

    String username;

    String password;

    String firstName;

    String lastName;
}