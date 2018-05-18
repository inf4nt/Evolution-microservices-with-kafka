package com.evolution.library.core.v5;

public interface RequestTransform<Key, RT extends RequestType, C extends Command> {

    C transform(Request<Key, RT> request);
}
