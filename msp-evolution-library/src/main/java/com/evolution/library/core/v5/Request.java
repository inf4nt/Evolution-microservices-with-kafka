package com.evolution.library.core.v5;

public interface Request<Key, RT extends RequestType> extends Base<Key> {

    RT getRequestType();
}
