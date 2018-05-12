package com.evolution.library.core.v4;

public interface Command<Key, RT extends RequestType, D extends Domain<Key>> extends Message<Key>, Operation {

    RT getRequestType();

    D getDomain();
}
