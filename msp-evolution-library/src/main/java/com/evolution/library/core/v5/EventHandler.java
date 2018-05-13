package com.evolution.library.core.v5;


public interface EventHandler<E extends Event, SE extends State> {

    SE handle(E event, SE state);
}
