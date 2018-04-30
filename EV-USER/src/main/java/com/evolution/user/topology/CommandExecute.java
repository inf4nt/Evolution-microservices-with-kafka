package com.evolution.user.topology;

import com.evolution.user.core.Base;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.Wither;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
public class CommandExecute implements Base<String> {

    @NotEmpty
    String key;

    @NotEmpty
    String operationNumber;

    @NotNull
    List<String> errors = new ArrayList<>();
}
