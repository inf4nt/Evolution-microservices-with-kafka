package com.evolution.direct.message.core;

public interface Event<Key, EventNumber> extends Base<Key> {

    EventNumber getEventNumber();
}

