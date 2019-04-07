package com.lagab.cmanager.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class DuplicateFileException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public DuplicateFileException(String fileName) {
        super(ErrorConstants.FILE_NOT_FOUND_TYPE, fileName, Status.BAD_REQUEST);
    }

    public DuplicateFileException(
        long companyId, long repositoryId, String fileName) {

        super(ErrorConstants.FILE_NOT_FOUND_TYPE,
            String.format(
                "{companyId=%s, repositoryId=%s, fileName=%s}", companyId,
                repositoryId, fileName), Status.BAD_REQUEST);
    }

    public DuplicateFileException(
        long companyId, long repositoryId, String fileName, String version) {

        super(ErrorConstants.FILE_NOT_FOUND_TYPE,
            String.format(
                "{companyId=%s, repositoryId=%s, fileName=%s, version=%s}",
                companyId, repositoryId, fileName, version), Status.BAD_REQUEST);
    }

    public DuplicateFileException(
        long companyId, long repositoryId, String fileName, String version,
        Throwable cause) {

        super(ErrorConstants.FILE_NOT_FOUND_TYPE,
            String.format(
                "{companyId=%s, repositoryId=%s, fileName=%s, version=%s, " +
                    "cause=%s}",
                companyId, repositoryId, fileName, version, cause)
            , Status.BAD_REQUEST);
    }
}
