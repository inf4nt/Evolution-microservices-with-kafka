package com.evolution.direct.message.base.core.command;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

import javax.validation.constraints.NotEmpty;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Wither
public class MessageCreateCommand implements IMessageCommand {

    @NotEmpty
    String key;

    @NotEmpty
    String text;

    @NotEmpty
    String sender;

    @NotEmpty
    String recipient;
}
