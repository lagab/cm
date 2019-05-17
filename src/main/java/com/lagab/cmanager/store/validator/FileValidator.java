package com.lagab.cmanager.store.validator;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * TODO : to continue
 */
public abstract class FileValidator {

    // Validation of file related input
    public static final String FILE_NAME_REGEX = "^[a-zA-Z0-9!@#$%^&{}\\[\\]()_+\\-=,.~'` ]{1,255}$";

    /**
     * The file extension that will be allowed by this validator
     */
    List<String> allowedExtensions = new ArrayList<String>();

    protected abstract boolean isSupportedContentType(String contentType);

    private boolean isSupportedExtension(String fileName){
        return false;
    }

    /**
     * Calls getValidFileName with the default list of allowedExtensions
     *
     * @param context
     * @param input
     * @param allowedExtensions
     * @param allowNull
     *
     * @return true if no validation exceptions occur
     */
    public boolean isValidFileName(String context, String input, List<String> allowedExtensions, boolean allowNull) {
        try {
            getValidFileName(context, input, allowedExtensions, allowNull);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns a canonicalized and validated file name as a String. Implementors should check for allowed file extensions here, as well as allowed file name characters, as declared in
     * "ESAPI.properties". Invalid input will generate a descriptive ValidationException, and input that is clearly an attack will generate a descriptive IntrusionException.
     *
     * Note: If you do not explicitly specify a white list of allowed extensions, all extensions will be allowed by default.
     *
     * @param context A descriptive name of the parameter that you are validating (e.g., LoginPage_UsernameField). This value is used by any logging or error handling that is done with respect to the
     * value passed in.
     * @param input The actual input data to validate.
     * @param allowedExtensions
     * @param allowNull If allowNull is true then an input that is NULL or an empty string will be legal. If allowNull is false then NULL or an empty String will throw a ValidationException.
     *
     * @return A valid file name
     *
     * @throws //ValidationException
     */
    public String getValidFileName(String context, String input, List<String> allowedExtensions, boolean allowNull) /*throws ValidationException*/ {

        String canonical = "";
        // detect path manipulation
        try {
            if (StringUtils.isEmpty(input)) {
                if (allowNull) {
                    return null;
                }
                //throw new ValidationException(context + ": Input file name required", "Input required: context=" + context + ", input=" + input, context);
            }

            // do basic validation
            canonical = new File(input).getCanonicalFile().getName();
            //getValidInput(context, input, FILE_NAME_REGEX, 255, true);

            File f = new File(canonical);
            String c = f.getCanonicalPath();
            String cpath = c.substring(c.lastIndexOf(File.separator) + 1);


            // the path is valid if the input matches the canonical path
            if (!input.equals(cpath)) {
                //throw new ValidationException(context + ": Invalid file name", "Invalid directory name does not match the canonical path: context=" + context + ", input=" + input + ", canonical=" + canonical, context);
            }

        } catch (IOException e) {
            //throw new ValidationException(context + ": Invalid file name", "Invalid file name does not exist: context=" + context + ", canonical=" + canonical, e, context);
        }

        // verify extensions
        if ((allowedExtensions == null) || (allowedExtensions.isEmpty())) {
            return canonical;
        } else {
            Iterator<String> i = allowedExtensions.iterator();
            while (i.hasNext()) {
                String ext = i.next();
                if (input.toLowerCase().endsWith(ext.toLowerCase())) {
                    return canonical;
                }
            }
            //throw new ValidationException(context + ": Invalid file name does not have valid extension ( " + allowedExtensions + ")", "Invalid file name does not have valid extension ( " + allowedExtensions + "): context=" + context + ", input=" + input, context);
        }
        return null;
    }
}
