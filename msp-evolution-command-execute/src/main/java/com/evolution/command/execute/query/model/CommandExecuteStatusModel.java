package com.evolution.command.execute.query.model;

import com.evolution.library.core.Command;
import com.evolution.library.core.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "command_execute_status")
public class CommandExecuteStatusModel implements Command<String, String> {

    @Id
    @NotEmpty
    String operationNumber;

    @Indexed(unique = true)
    @NotEmpty
    String key;

    @NotNull
    List<String> errors = new ArrayList<>();
}
