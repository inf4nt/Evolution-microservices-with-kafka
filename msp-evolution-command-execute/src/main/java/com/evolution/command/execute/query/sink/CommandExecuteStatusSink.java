package com.evolution.command.execute.query.sink;

import com.evolution.command.execute.query.model.CommandExecuteStatusModel;
import com.evolution.command.execute.query.repository.CommandExecuteStatusRepository;
import com.evolution.library.core.CommandExecuteStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.util.UUID;

@EnableBinding(Sink.class)
public class CommandExecuteStatusSink {

    private static final Logger logger = LoggerFactory.getLogger(CommandExecuteStatusSink.class);

    @Autowired
    private CommandExecuteStatusRepository commandExecuteStatusRepository;

    @StreamListener(Sink.INPUT)
    public void sinkState(CommandExecuteStatus command) {
        logger.info("Catch CommandExecuteStatus:" + command);
        CommandExecuteStatusModel model = new CommandExecuteStatusModel();
        model.setKey(command.getOperationNumber());
        model.setOperationNumber(command.getOperationNumber());
        model.setErrors(command.getErrors());

        commandExecuteStatusRepository.save(model);
    }
}
