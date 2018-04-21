package evolution.coreold;

import org.apache.kafka.streams.StreamsConfig;

public interface Topology {

    StreamsConfig streamsConfig();

    void init();
}
