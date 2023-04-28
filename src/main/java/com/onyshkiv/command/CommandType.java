package com.onyshkiv.command;


import com.onyshkiv.command.impl.*;
import com.onyshkiv.command.impl.admin.ChangeUserStatusCommand;
import com.onyshkiv.command.impl.admin.GetLibrariansCommand;
import com.onyshkiv.command.impl.admin.GetReadersCommand;
import com.onyshkiv.command.impl.librarian.DeleteUserCommand;
import com.onyshkiv.command.impl.librarian.GetUsersBooksCommand;
import com.onyshkiv.command.impl.librarian.GiveBookCommand;
import com.onyshkiv.command.impl.librarian.OrdersPageCommand;
import com.onyshkiv.command.impl.reader.AddBookCommand;
import com.onyshkiv.command.impl.reader.GetUserBookCommand;

public enum CommandType {
    SIGNUPPOST(new RegistrationCommand()),
    SIGNINPOST(new LoginCommand()),
    SIGNOUTGET(new SignOutCommand()),
    USERBOOKSGET(new GetUserBookCommand()),
    BOOKPAGEGET(new BooksPageCommand()),
    ADDBOOKGET(new AddBookCommand()),
    EDITPROFILEPOST(new EditProfileCommand()),
    CHANGEPASSWORDPOST(new ChangePasswordCommand()),
    GETORDERSGET(new OrdersPageCommand()),
    //GIVEBOOKPOST(new GiveBookCommand());
    GIVEBOOKGET(new GiveBookCommand()),
    GETUSERSBOOKGET(new GetUsersBooksCommand()),
    GETLIBRARIANSGET(new GetLibrariansCommand()),
    GETREADERSGET(new GetReadersCommand()),
    DELETEUSERGET(new DeleteUserCommand()),
    USERINFOGET(new GetUserInfoCommand()),
    CHANGEUSERSTATUSGET(new ChangeUserStatusCommand());
    private Command command;

    CommandType(Command command){
        this.command= command;
    }

    public static Command getCurrentCommand(String action){
        return CommandType.valueOf(action.toUpperCase()).command;
    }

}
