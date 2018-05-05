package com.evolution.core.share;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotEmpty
    String key;

    @NotEmpty
    String firstName;

    @NotEmpty
    String lastName;
}
