package com.lagab.cmanager.store.errors;

import com.lagab.cmanager.web.rest.errors.SystemException;

/**
 * @author gabriel
 * @since 17/04/2019.
 */
public class InvalidExtensionException extends SystemException{

    public InvalidExtensionException() {
        super();
    }

    public InvalidExtensionException(String fileName) {
        super("extension "+ fileName +" are invalid ");
    }

    public InvalidExtensionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidExtensionException(Throwable cause) {
        super(cause);
    }
}
