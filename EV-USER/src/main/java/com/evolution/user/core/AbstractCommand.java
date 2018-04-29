package com.evolution.user.core;

import com.evolution.user.topology.core.common.CommandErrors;
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
public class AbstractCommand implements CommandValidationResponse {

    @NotEmpty
    String key;

    @NotEmpty
    String operationNumber;

    @NotNull
    List<CommandErrors> errors = new ArrayList<>();
}
