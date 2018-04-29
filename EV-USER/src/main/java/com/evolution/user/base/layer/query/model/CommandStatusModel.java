package com.evolution.user.base.layer.query.model;

import com.evolution.user.topology.core.common.CommandErrors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "command_status")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandStatusModel {

    @Id
    String operationNumber;

    List<CommandErrors> errors = new ArrayList<>();
}
