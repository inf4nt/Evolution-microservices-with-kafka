package com.evolution.library.core;

public interface Command<Key, OperationNumber> extends Base<Key> {

    OperationNumber getOperationNumber();
}
