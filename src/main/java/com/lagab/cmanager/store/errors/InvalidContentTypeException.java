package com.lagab.cmanager.store.errors;

import com.lagab.cmanager.web.rest.errors.SystemException;

/**
 * @author gabriel
 * @since 17/04/2019.
 */
public class InvalidContentTypeException extends SystemException{

    public InvalidContentTypeException() {
    }

    public InvalidContentTypeException(String message) {
        super(message);
    }

    public InvalidContentTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidContentTypeException(Throwable cause) {
        super(cause);
    }
}
