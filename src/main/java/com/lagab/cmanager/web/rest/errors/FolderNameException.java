package com.lagab.cmanager.web.rest.errors;

public class FolderNameException extends Exception{

    public FolderNameException() {
        super();
    }
    public FolderNameException(String msg) {
        super(msg);
    }
    public FolderNameException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public FolderNameException(Throwable cause) {
        super(cause);
    }
}
