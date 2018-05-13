package com.evolution.library.core.v5;

public interface CommandHandler<C extends Command, E extends Event> {

    E handle(C command);
}
