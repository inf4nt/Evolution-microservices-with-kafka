package com.evolution.user.base.core.command.validation;

import com.evolution.user.base.core.common.CommandErrors;
import com.evolution.user.core.Base;

import java.util.List;

public interface CommandValidationResponse extends Base<String> {

    List<CommandErrors> getErrors();

    String getOperationNumber();
}
