package com.evolution.user.core;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandResponse {

    public final String URL;

    @Getter
    public final String operationNumber;

    public static CommandResponse build() {
        final String n = UUID.randomUUID().toString().replace("-", "");
        final String url = "localhost:8181/state/command-result/" + n;
        return new CommandResponse(url, n);
    }
}
