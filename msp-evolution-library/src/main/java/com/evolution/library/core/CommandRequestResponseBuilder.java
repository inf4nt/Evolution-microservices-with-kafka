package com.evolution.library.core;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class CommandRequestResponseBuilder {

    private static final String COMMAND_SERVICE_RESOURCE = "http://localhost:8089/state/execute_status/";

    public static CommandRequestDTO build() {
        return CommandRequestDTO.builder()
                .operationNumber(UUID.randomUUID().toString().replace("-", "")).build();
    }

    public static ResponseEntity<ResourceSupport> response(CommandRequestDTO commandRequestDTO) {
        ResourceSupport response = new ResourceSupport();
        Link selfRel = new Link(COMMAND_SERVICE_RESOURCE + commandRequestDTO.getOperationNumber(), "rel-to");
        response.add(selfRel);
        return new ResponseEntity<>(response, HttpStatus.SEE_OTHER);
    }
}
