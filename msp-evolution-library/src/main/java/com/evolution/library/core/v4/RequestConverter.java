package com.evolution.library.core.v4;

public interface RequestConverter<C extends Command> {

    C transform();
}
