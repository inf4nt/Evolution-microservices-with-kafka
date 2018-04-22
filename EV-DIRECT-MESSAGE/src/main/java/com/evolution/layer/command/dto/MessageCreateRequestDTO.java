package com.evolution.layer.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageCreateRequestDTO {

    @NotEmpty
    String text;

    @NotEmpty
    String recipient;

    @NotEmpty
    String sender;
}
