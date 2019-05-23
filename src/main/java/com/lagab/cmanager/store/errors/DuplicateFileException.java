package com.lagab.cmanager.store.errors;

import com.lagab.cmanager.web.rest.errors.SystemException;

/**
 * @author gabriel
 * @since 17/04/2019.
 */
public class DuplicateFileException extends SystemException{

    public DuplicateFileException() {
        super();
    }

    public DuplicateFileException(String message) {
        super("File already exists :" + message);
    }

    public DuplicateFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateFileException(Throwable cause) {
        super(cause);
    }
}
