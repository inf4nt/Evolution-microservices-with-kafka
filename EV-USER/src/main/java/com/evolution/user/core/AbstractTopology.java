package com.evolution.user.core;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTopology {

    private final Class<? extends Serde> keySerde;

    private final Class<? extends Serde> valueSerde;

    private final String applicationId;

    private final String groupId;

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaBootstrapServer;

    protected AbstractTopology(String applicationId, String groupId, Class<? extends Serde> keySerde, Class<? extends Serde> valueSerde) {
        this.keySerde = keySerde;
        this.valueSerde = valueSerde;
        this.applicationId = applicationId;
        this.groupId = groupId;
    }

    protected StreamsConfig streamsConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.CLIENT_ID_CONFIG, "Evolution");

        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServer);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, keySerde);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, valueSerde);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.evolution.*");

        return new StreamsConfig(props);
    }

    @PostConstruct
    public abstract void init();
}
