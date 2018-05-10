package com.evolution.library.core.v3.global;

import com.evolution.library.core.v3.Base;
import com.evolution.library.core.v3.MessageService;

public interface GlobalMessage<Key> extends Base<Key> {

    String getCorrelation();

    String getMessageType();

    default String getFeed() {
        return MessageService.getFeed(this.getClass());
    }

    default String random() {
        return MessageService.random();
    }
}
