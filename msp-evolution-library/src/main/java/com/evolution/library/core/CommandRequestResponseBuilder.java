package com.evolution.library.core;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class CommandRequestResponseBuilder {

    public static CommandRequestDTO build(RequestDTO request) {
        return CommandRequestDTO.builder()
                .request(request)
                .operationNumber(UUID.randomUUID().toString().replace("-", "")).build();
    }

    public static ResponseEntity<CommandResponseDTO> response(CommandRequestDTO commandRequestDTO) {
        CommandResponseDTO response = new CommandResponseDTO();
        Link selfRel = new Link("http://localhost:8089/state/execute_status/" + commandRequestDTO.getOperationNumber(), "rel-to");
        response.add(selfRel);
        return new ResponseEntity<>(response, HttpStatus.SEE_OTHER);
    }
}
