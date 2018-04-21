package com.evolution.layer.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageCreateRequestDTO {

    String text;

    String recipient;

    String sender;
}
