package com.onyshkiv.command;

import com.onyshkiv.command.impl.LoginCommand;

public enum CommandType {
    SIGNINPOST(new LoginCommand());

    private Command command;

    CommandType(Command command){
        this.command= command;
    }

    public static Command getCurrentCommand(String action){
        return CommandType.valueOf(action.toUpperCase()).command;
    }

}
