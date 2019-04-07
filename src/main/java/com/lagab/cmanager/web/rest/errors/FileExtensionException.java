package com.lagab.cmanager.web.rest.errors;

public class FileExtensionException extends Exception{

    public FileExtensionException() {
        super();
    }
    public FileExtensionException(String msg) {
        super(msg);
    }
    public FileExtensionException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public FileExtensionException(Throwable cause) {
        super(cause);
    }
}
