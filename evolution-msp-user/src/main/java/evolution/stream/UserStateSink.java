package evolution.stream;

import evolution.write.service.UserState;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class UserStateSink {

    @StreamListener(Sink.INPUT)
    public void listenerState(UserState state) {
        System.out.println("Catch state:" + state);
    }
}
