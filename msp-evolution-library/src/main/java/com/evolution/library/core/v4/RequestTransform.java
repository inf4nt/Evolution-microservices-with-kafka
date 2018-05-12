package com.evolution.library.core.v4;

public interface RequestTransform<C extends Command> {

    C transform(Request request);
}
