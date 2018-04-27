package com.evolution.direct.message.base.layer.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

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