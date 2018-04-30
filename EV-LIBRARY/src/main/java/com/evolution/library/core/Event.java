package com.evolution.library.core;

public interface Event<Key, EventNumber> extends Base<Key> {

    EventNumber getEventNumber();
}
