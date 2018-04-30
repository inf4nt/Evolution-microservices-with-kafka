package com.evolution.library.core;

public interface State<Key, EventNumber> extends Base<Key> {

    EventNumber getEventNumber();
}
