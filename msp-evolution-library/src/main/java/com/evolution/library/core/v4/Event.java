package com.evolution.library.core.v4;

public interface Event<Key, RT extends RequestType, D extends Domain<Key>, ES extends EventStatus> extends Message<Key>, Operation {

    RT getRequestType();

    D getDomain();

    ES getUserEventStatus();
}