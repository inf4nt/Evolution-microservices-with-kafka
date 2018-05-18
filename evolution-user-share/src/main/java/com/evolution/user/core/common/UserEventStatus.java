package com.evolution.user.core.common;

import com.evolution.library.core.v5.EventStatus;

public enum UserEventStatus implements EventStatus {

    USER_BY_KEY_NOT_FOUND,
    USER_BY_USER_NAME_ALREADY_EXIST,
    PROGRESS
}
