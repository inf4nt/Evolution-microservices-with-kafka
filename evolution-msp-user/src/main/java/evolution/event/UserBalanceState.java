package evolution.event;

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
public class UserBalanceState implements BaseFeed<String> {

    String key;

    String username;

    Long balance;
}
