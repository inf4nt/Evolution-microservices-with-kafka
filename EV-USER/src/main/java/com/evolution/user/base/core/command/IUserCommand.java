package com.evolution.user.base.core.command;


import com.evolution.user.core.Base;

public interface IUserCommand extends Base<String> {

    String getOperationNumber();
}
