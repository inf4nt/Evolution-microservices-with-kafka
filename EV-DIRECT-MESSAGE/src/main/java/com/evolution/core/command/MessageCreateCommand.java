package com.evolution.core.command;

import com.evolution.core.base.Base;
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
public class MessageCreateCommand implements Base<String> {

    @NotEmpty
    String key;

    @NotEmpty
    String text;

    @NotEmpty
    String sender;

    @NotEmpty
    String recipient;
}
