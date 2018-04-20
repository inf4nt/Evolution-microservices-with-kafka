package com.evolution.user.kafka.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractTopology {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${spring.application.name}")
    private String applicationName;

    private final String applicationId = this.getClass().getSimpleName();

    private final Class<? extends Serde> keySerdes;

    private final Class<? extends Serde> valueSerdes;

    private String groupId;

    private boolean isRandomGroupId;

    public AbstractTopology(Class<? extends Serde> valueSerdes, Class<? extends Serde> keySerdes, String groupId) {
        this.valueSerdes = valueSerdes;
        this.keySerdes = keySerdes;
        this.groupId = groupId;
    }

    public AbstractTopology(Class<? extends Serde> valueSerdes, Class<? extends Serde> keySerdes, boolean isRandomGroupId) {
        this.valueSerdes = valueSerdes;
        this.keySerdes = keySerdes;
        this.isRandomGroupId = isRandomGroupId;
    }

    public StreamsConfig streamsConfig() {
        Map<String, Object> props = new HashMap<>();


        props.put(StreamsConfig.CLIENT_ID_CONFIG, applicationName);

        if (isRandomGroupId) {
            props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        } else {
            props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        }


        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        props.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 100);
//        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 1000);

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");


        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, keySerdes);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, valueSerdes);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return new StreamsConfig(props);
    }

    @PostConstruct
    public abstract void init();
}
