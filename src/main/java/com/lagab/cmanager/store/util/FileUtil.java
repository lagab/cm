package com.lagab.cmanager.store.util;

import org.springframework.util.FileSystemUtils;

import java.io.File;

/**
 * @author gabriel
 * @since 18/04/2019.
 */
public class FileUtil {

    public static final String TMP_DIR = "/tmp";

    public static File createTempFile(byte[] bytes) {
        return null;
    }

    public static void delete(File file) {
        FileSystemUtils.deleteRecursively(file);
    }
}
