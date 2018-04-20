package com.evolution.direct.message.layer.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageUpdateTextRequestDTO {

    String id;

    String text;
}
