package com.evolution.core.event;

import com.evolution.core.command.MessageCreateCommand;
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
public class MessageCreateEvent implements IMessageEvent {

    @NotEmpty
    String key;

    @NotEmpty
    String eventNumber;

    @NotEmpty
    MessageCreateCommand messageCreateCommand;
}
