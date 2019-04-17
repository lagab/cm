package com.lagab.cmanager.web.rest.errors.store;

/**
 * @author gabriel
 * @since 17/04/2019.
 */
public class DuplicateFileException extends Exception{

    public DuplicateFileException() {
    }

    public DuplicateFileException(String message) {
        super(message);
    }

    public DuplicateFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateFileException(Throwable cause) {
        super(cause);
    }
}
