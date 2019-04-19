package com.lagab.cmanager.store.errors;

/**
 * @author gabriel
 * @since 17/04/2019.
 */
public class FileNameException extends Exception{

    public FileNameException() {
    }

    public FileNameException(String message) {
        super(message);
    }

    public FileNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNameException(Throwable cause) {
        super(cause);
    }
}
