package com.lagab.cmanager.store.validator;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public class ImageFileValidator extends FileValidator /*implements ConstraintValidator<, MultipartFile>*/ {


    /*@Override
    public void initialize(Annotation constraintAnnotation) {

    }

    @Override*/
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }

    protected boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/png")
            || contentType.equals("image/jpg")
            || contentType.equals("image/jpeg");
    }
}
