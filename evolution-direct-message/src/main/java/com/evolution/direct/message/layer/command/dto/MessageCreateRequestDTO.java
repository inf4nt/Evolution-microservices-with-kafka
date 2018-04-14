package com.evolution.direct.message.layer.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageCreateRequestDTO {

    String text;

    String sender;

    String recipient;
}
