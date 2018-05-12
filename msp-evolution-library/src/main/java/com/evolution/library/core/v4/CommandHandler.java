package com.evolution.library.core.v4;

public interface CommandHandler<C extends Command, E extends Event> {

    E handle (C command);
}
