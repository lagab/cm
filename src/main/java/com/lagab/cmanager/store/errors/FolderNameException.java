package com.lagab.cmanager.store.errors;

import com.lagab.cmanager.web.rest.errors.SystemException;

/**
 * @author gabriel
 * @since 17/04/2019.
 */
public class FolderNameException extends SystemException{

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
