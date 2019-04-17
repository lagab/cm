package com.lagab.cmanager.web.rest.errors.store;

/**
 * @author gabriel
 * @since 17/04/2019.
 */
public class FolderNameException extends Exception{

    public FolderNameException() {
    }

    public FolderNameException(String message) {
        super(message);
    }

    public FolderNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public FolderNameException(Throwable cause) {
        super(cause);
    }
}
