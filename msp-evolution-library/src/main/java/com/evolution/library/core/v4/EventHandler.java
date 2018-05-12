package com.evolution.library.core.v4;

public interface EventHandler<E extends Event, SE extends StateEvent> {

    SE handle(E event, SE state);
}
