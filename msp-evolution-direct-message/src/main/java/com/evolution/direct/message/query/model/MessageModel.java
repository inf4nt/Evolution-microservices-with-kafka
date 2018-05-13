package com.evolution.direct.message.query.model;

import com.evolution.direct.message.core.domain.User;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotEmpty;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Wither
public class MessageModel {

    @Id
    String key;

    @Indexed(unique = true)
    String eventNumber;

    @NotEmpty
    String text;

    @NotEmpty
    User sender;

    @NotEmpty
    User recipient;

    @NotEmpty
    boolean isRead;
}
