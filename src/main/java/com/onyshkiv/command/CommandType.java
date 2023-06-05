package com.onyshkiv.command;


import com.onyshkiv.command.impl.*;
import com.onyshkiv.command.impl.admin.*;
import com.onyshkiv.command.impl.admin.manage_books.*;
import com.onyshkiv.command.impl.librarian.*;
import com.onyshkiv.command.impl.reader.AddBookCommand;
import com.onyshkiv.command.impl.reader.GetUserBookCommand;

public enum CommandType {
    SIGNUPPOST(new RegistrationCommand()), // common
    SIGNINPOST(new LoginCommand()),// common
    SIGNOUTGET(new SignOutCommand()),// common
    USERBOOKSGET(new GetUserBookCommand()),// reader
    BOOKPAGEGET(new BooksPageCommand()),// common
    ADDBOOKPOST(new AddBookCommand()),// reader
    EDITPROFILEPOST(new EditProfileCommand()),// common
    CHANGEPASSWORDPOST(new ChangePasswordCommand()),// common
    GETORDERSGET(new OrdersPageCommand()),// librarian
    //GIVEBOOKPOST(new GiveBookCommand());
    GIVEBOOKPOST(new GiveBookCommand()),// librarian
    GETUSERSBOOKGET(new GetUsersBooksCommand()),// librarian
    GETLIBRARIANSGET(new GetLibrariansCommand()),// admin
    GETREADERSGET(new GetReadersCommand()),// admin
    DELETEUSERGET(new DeleteUserCommand()),// admin(delete librarian)
    USERINFOGET(new GetUserInfoCommand()), // librarian and admin
    CHANGEUSERSTATUSGET(new ChangeUserStatusCommand()), // admin
    GIVEBOOKBACKGET(new GiveBookBackCommand()),// librarian
    ADDBOOKPAGEGET(new GetAddBookPageCommand()),// admin
    CREATEBOOKPOST(new CreateBookCommand()),// admin
    AUTHANDPUBGET(new GetAuthorsAndPublicationsCommand()),// admin
    CREATEAUTHORPOST(new CreateAuthorCommand()),//admin
    CREATEPUBLICATIONPOST(new CreatePublicationCommand()),//admin
    RENAMEPUBLICATIONPOST(new RenamePublicationCommand()),//admin
    RENAMEAUTHORPOST(new RenameAuthorCommand()),//admin
    EDITBOOKPAGEGET(new GetEditBookPageCommand()),//admin
    EDITBOOKPOST(new EditBookCommand()), //admin
    DELETEBOOKPOST(new DeleteBookCommand());//admin
    private Command command;

    CommandType(Command command){
        this.command= command;
    }

    public static Command getCurrentCommand(String action){
        return CommandType.valueOf(action.toUpperCase()).command;
    }

}
