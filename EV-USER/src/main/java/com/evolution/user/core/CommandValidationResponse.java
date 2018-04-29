package com.evolution.user.core;

import com.evolution.user.topology.core.common.CommandErrors;

import java.util.List;

public interface CommandValidationResponse extends Base<String> {

    List<CommandErrors> getErrors();

    String getOperationNumber();
}
