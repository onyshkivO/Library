package com.onyshkiv.command;


import com.onyshkiv.command.impl.*;

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
    DELETEUSERGET(new DeleteUserCommand());

    private Command command;

    CommandType(Command command){
        this.command= command;
    }

    public static Command getCurrentCommand(String action){
        return CommandType.valueOf(action.toUpperCase()).command;
    }

}
