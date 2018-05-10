package com.evolution.library.core.v3;

public interface State<Key> extends Base<Key>, Domain {

    String getEventNumber();
}
