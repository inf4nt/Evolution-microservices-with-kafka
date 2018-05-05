package com.evolution.library.core;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.hateoas.ResourceSupport;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CommandResponseDTO extends ResourceSupport {

    String link;
}
