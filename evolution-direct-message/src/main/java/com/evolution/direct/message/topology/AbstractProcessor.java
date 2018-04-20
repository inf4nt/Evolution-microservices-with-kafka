package com.evolution.direct.message.topology;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractProcessor {

    private final String applicationId;

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${spring.application.name}")
    private String applicationName;

    public AbstractProcessor(String applicationId) {
        this.applicationId = applicationId;
    }

    public StreamsConfig streamsConfig() {
        Map<String, Object> props = new HashMap<>();


        props.put(StreamsConfig.CLIENT_ID_CONFIG, applicationName);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 2000);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000);

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new StreamsConfig(props);
    }

    public abstract void init();
}
