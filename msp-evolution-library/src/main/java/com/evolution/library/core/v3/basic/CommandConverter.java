package com.evolution.library.core.v3.basic;

import com.evolution.library.core.v3.global.GlobalCommand;

public interface CommandConverter<Key, AbstractCommandType extends GlobalCommand<Key>> {

    AbstractCommandType convert(Command<Key, AbstractCommandType> command);
}
