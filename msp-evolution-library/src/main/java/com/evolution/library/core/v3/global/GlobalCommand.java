package com.evolution.library.core.v3.global;

import com.evolution.library.core.v3.Domain;

public interface GlobalCommand<Key> extends GlobalMessage<Key>, Domain {

   String getCommandNumber();
}
