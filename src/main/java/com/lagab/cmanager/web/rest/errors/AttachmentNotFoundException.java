package com.lagab.cmanager.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class AttachmentNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public AttachmentNotFoundException() {
        super(null, "Attachement not found", Status.NOT_FOUND);
    }
}
