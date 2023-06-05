package com.onyshkiv.controller;

import com.onyshkiv.command.Command;
import com.onyshkiv.command.CommandFactory;
import com.onyshkiv.command.CommandResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet({"/controller"})
public class Controller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        CommandFactory commandFactory = CommandFactory.getInstance();
        Command comand = commandFactory.defineCommand(request);

        CommandResult commandResult = comand.execute(request, response);
        System.out.println(commandResult.getPage());
        if (commandResult.isRedirect()){
            response.sendRedirect(request.getContextPath()+commandResult.getPage());
        }
        else {
            request.getServletContext().getRequestDispatcher(commandResult.getPage()).forward(request, response);
        }
    }
}
