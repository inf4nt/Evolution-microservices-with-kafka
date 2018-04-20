package com.evolution.msp.user.coreold;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTopology implements Topology {

    private final String applicationId;

    private final Class<? extends Serde> serdeKey;

    private final Class<? extends Serde> serdeValue;

    private final String groupId;

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${spring.application.name}")
    private String applicationName;

    protected AbstractTopology(String applicationId, String groupId, Class<? extends Serde> serdeKey, Class<? extends Serde> serdeValue) {
        this.applicationId = applicationId;
        this.serdeKey = serdeKey;
        this.serdeValue = serdeValue;
        this.groupId = groupId;
    }

    @Override
    public StreamsConfig streamsConfig() {
        Map<String, Object> props = new HashMap<>();

        props.put(StreamsConfig.CLIENT_ID_CONFIG, applicationId);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100);

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, serdeKey);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, serdeValue);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new StreamsConfig(props);
    }

    @Override
    @PostConstruct
    public abstract void init();
}
