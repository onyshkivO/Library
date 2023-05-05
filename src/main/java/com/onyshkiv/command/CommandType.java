package com.onyshkiv.command;


import com.onyshkiv.command.impl.*;
import com.onyshkiv.command.impl.admin.*;
import com.onyshkiv.command.impl.librarian.*;
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
    CHANGEUSERSTATUSGET(new ChangeUserStatusCommand()),
    GIVEBOOKBACKGET(new GiveBookBackCommand()),
    ADDBOOKPAGEGET(new GetAddBookPageCommand()),
//    ADDBOOKPAGEPOST(new GetAddBookPageCommand()), //todo якось треба переробити
    CREATEBOOKPOST(new CreateBookCommand()),
    AUTHANDPUBGET(new GetAuthorsAndPublicationsCommand()),
    CREATEAUTHORPOST(new CreateAuthorCommand()),
    CREATEPUBLICATIONPOST(new CreatePublicationCommand()),
    RENAMEPUBLICATIONPOST(new RenamePublicationCommand()),
    RENAMEAUTHORPOST(new RenameAuthorCommand()),
    EDITBOOKPAGEGET(new GetEditBookPageCommand()),
    EDITBOOKPOST(new EditBookCommand());
    private Command command;

    CommandType(Command command){
        this.command= command;
    }

    public static Command getCurrentCommand(String action){
        return CommandType.valueOf(action.toUpperCase()).command;
    }

}
