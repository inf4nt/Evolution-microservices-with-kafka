package com.evolution.user.base.stream;

import com.evolution.user.base.layer.query.model.CommandStatusModel;
import com.evolution.user.base.layer.query.repository.CommandRepository;
import com.evolution.user.core.AbstractCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import javax.validation.Valid;

@EnableBinding(CommandSink.class)
public class CommandResponseStream {

    private static final Logger logger = LoggerFactory.getLogger(CommandResponseStream.class);

    @Autowired
    private CommandRepository commandRepository;

    @StreamListener(CommandSink.INPUT_COMMAND_STATUS)
    public void sink(@Valid AbstractCommand command) {
        logger.info("Catch AbstractCommand:" + command);

        CommandStatusModel model = new CommandStatusModel();
        model.setOperationNumber(command.getOperationNumber());
        model.setErrors(command.getErrors());

        commandRepository.save(model);
    }
}
