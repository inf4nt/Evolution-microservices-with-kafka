package com.evolution.layer.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageUpdateTextRequestDTO {

    @NotEmpty
    String key;

    @NotEmpty
    String text;
}
