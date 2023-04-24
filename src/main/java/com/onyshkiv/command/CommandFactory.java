package com.onyshkiv.command;

import com.onyshkiv.command.impl.EmptyCommand;
import jakarta.servlet.http.HttpServletRequest;

import java.net.http.HttpRequest;

public class CommandFactory {
    private static CommandFactory instance;

    public static synchronized CommandFactory getInstance() {
        if (instance == null) {
            instance = new CommandFactory();
        }
        return instance;
    }

    private CommandFactory() {
    }
    public Command defineCommand(HttpServletRequest request) {
        Command command = new EmptyCommand();
        String action = request.getParameter("action");
        System.out.println(action);
        action += request.getContentType() == null ? "get" : "post";
        System.out.println(action);
        try {
            command = CommandType.getCurrentCommand(action.toUpperCase());
        }catch (IllegalArgumentException e){
            //log
        }
        return command;
    }
}
