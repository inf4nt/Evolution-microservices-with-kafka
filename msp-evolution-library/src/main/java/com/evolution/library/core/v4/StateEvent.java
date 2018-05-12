package com.evolution.library.core.v4;

public interface StateEvent<Key, RT extends RequestType, D extends Domain<Key>> extends Message<Key> {

    RT getRequestType();

    D getDomain();
}
