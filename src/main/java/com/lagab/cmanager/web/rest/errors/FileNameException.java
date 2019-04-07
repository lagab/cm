package com.lagab.cmanager.web.rest.errors;

public class FileNameException extends Exception{

    public FileNameException() {
        super();
    }
    public FileNameException(String msg) {
        super(msg);
    }
    public FileNameException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public FileNameException(Throwable cause) {
        super(cause);
    }
}
