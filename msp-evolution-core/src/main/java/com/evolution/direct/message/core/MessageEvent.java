package com.evolution.direct.message.core;

import com.evolution.direct.message.core.common.MessageEventStatus;
import com.evolution.direct.message.core.common.MessageRequestTypes;
import com.evolution.library.core.v4.Event;

public class MessageEvent implements Event<String, MessageRequestTypes, MessageDomain, MessageEventStatus> {
    @Override
    public MessageRequestTypes getRequestType() {
        return null;
    }

    @Override
    public MessageDomain getDomain() {
        return null;
    }

    @Override
    public MessageEventStatus getUserEventStatus() {
        return null;
    }

    @Override
    public String getCorrelation() {
        return null;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getOperationNumber() {
        return null;
    }
}
