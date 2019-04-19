package com.lagab.cmanager.store.errors;

/**
 * @author gabriel
 * @since 17/04/2019.
 */
public class FileSizeException extends Exception{

    public FileSizeException() {
    }

    public FileSizeException(String message) {
        super(message);
    }

    public FileSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSizeException(Throwable cause) {
        super(cause);
    }
}
