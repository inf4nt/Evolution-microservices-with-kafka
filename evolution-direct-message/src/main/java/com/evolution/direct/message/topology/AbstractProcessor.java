package com.evolution.direct.message.topology;


import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractProcessor {

    private final String applicationId;

    private final String groupId;

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${spring.application.name}")
    private String applicationName;

    public AbstractProcessor(String applicationId, String groupId) {
        this.applicationId = applicationId;
        this.groupId = groupId;
    }

    public StreamsConfig streamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.CLIENT_ID_CONFIG, applicationName);
        props.put("group.id", groupId);
        props.put("auto.offset.reset", "earliest");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new StreamsConfig(props);
    }

    public abstract void init();
}
