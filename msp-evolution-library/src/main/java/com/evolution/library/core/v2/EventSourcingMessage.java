package com.evolution.library.core.v2;


public interface EventSourcingMessage<Key> extends Base<Key> {

    String getCorrelationId();

    default String getFeed() {
        return EventSourcingService.getFeed(this.getClass());
    }

    default String generateRundom() {
        return EventSourcingService.generateRundom();
    }
}
