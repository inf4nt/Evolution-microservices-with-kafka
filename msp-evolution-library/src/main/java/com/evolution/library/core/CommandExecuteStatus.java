package com.evolution.library.core;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

import java.util.ArrayList;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@Wither
public class CommandExecuteStatus implements Command<String, String> {

    String key;

    String operationNumber;

    List<String> errors = new ArrayList<>();
}
