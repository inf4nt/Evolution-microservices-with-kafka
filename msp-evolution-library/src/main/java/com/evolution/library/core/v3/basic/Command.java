package com.evolution.library.core.v3.basic;

import com.evolution.library.core.v3.global.GlobalCommand;

public interface Command<Key, AbstractCommandType extends GlobalCommand<Key>> extends CommandConverter<Key, AbstractCommandType> {
}
