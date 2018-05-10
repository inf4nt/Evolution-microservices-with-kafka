package com.evolution.library.core.v3.global;

import com.evolution.library.core.v3.Domain;

public interface GlobalEvent<Key> extends GlobalMessage<Key>, Domain {

    String getEventNumber();
}
