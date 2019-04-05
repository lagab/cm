package com.lagab.cmanager.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class NoSuchFileException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public NoSuchFileException(String fileName) {
        super(ErrorConstants.FILE_NOT_FOUND_TYPE, fileName, Status.BAD_REQUEST);
    }
}
