package com.onyshkiv.command;

public class CommandResult {
    private String page;
    private boolean isRedirected;

    public CommandResult(String page){
        this.page =page;
    }

    public CommandResult(String page, boolean isRedirected){
        this.page=page;
        this.isRedirected =isRedirected;
    }

    public void setPage(String page){
        this.page=page;
    }
    public String getPage(){
        return page;
    }

    public boolean isRedirect(){
        return isRedirected;
    }
}
