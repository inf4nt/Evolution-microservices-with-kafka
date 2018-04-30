package com.evolution.library.core;

import java.util.List;

public interface CommandExecuteResponse<Key, OperationNumber> extends Command<Key, OperationNumber> {

    List<String> getErrors();
}
