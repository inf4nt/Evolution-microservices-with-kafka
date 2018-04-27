package com.evolution.user.core;

public interface Event<Key, EventNumber> extends Base<Key> {

    EventNumber getEventNumber();
}
