package com.evolution.core.base;

public interface State<Key, EventNumber> extends Base<Key> {

    EventNumber getEventNumber();
}
