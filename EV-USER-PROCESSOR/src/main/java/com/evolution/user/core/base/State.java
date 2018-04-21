package com.evolution.user.core.base;

public interface State<Key, EventNumber> extends Base<Key> {

    EventNumber getEventNumber();
}
