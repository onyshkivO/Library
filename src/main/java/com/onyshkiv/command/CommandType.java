package com.onyshkiv.command;


import com.onyshkiv.command.impl.LoginCommand;
import com.onyshkiv.command.impl.RegistrationCommand;

public enum CommandType {
    SIGNUPPOST(new RegistrationCommand()),
    SIGNINPOST(new LoginCommand());

    private Command command;

    CommandType(Command command){
        this.command= command;
    }

    public static Command getCurrentCommand(String action){
        return CommandType.valueOf(action.toUpperCase()).command;
    }

}
