package com.evolution.library.core.v5;

public interface State<Key, C extends Content> extends Base<Key> {

    String getEventNumber();

    C getContent();
}
