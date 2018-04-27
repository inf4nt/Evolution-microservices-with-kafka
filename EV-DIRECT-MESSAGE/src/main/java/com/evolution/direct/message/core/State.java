package com.evolution.direct.message.core;

public interface State<Key, EventNumber> extends Base<Key> {

    EventNumber getEventNumber();
}
