package com.lagab.cmanager.web.rest.errors;

/**
 * @author gabriel
 * @since 17/04/2019.
 */
public class SystemException extends Exception{

    public SystemException() {
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }
}
