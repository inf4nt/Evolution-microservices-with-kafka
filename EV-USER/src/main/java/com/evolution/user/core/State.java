package com.evolution.user.core;

public interface State<Key, EventNumber> extends Base<Key> {

    EventNumber getEventNumber();
}
