package com.lagab.cmanager.web.rest.errors;

public class FileSizeException extends Exception{

    public FileSizeException() {
        super();
    }
    public FileSizeException(String msg) {
        super(msg);
    }
    public FileSizeException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public FileSizeException(Throwable cause) {
        super(cause);
    }
}
