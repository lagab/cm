package com.lagab.cmanager.web.rest.util;

import com.lagab.cmanager.service.Store;
import com.lagab.cmanager.web.rest.errors.InternalServerErrorException;
import com.lagab.cmanager.web.rest.errors.SystemException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.InputStream;

/**
 * @author GABRIEL
 * @since 05/04/2019.
 */
public class StoreUtil {

    /**
     * Adds a directory.
     *
     * @param workspaceId the primary key of the workspace
     * @param projectId the primary key of the data project (optionally)
     * @param dirName the directory's name
     */
    public static void addDirectory(
        long workspaceId, long projectId, String dirName)
        throws InternalServerErrorException {

        getStore().addDirectory(workspaceId, projectId, dirName);
    }

    /**
     * Adds a file based on a byte array.
     *
     * @param workspaceId the primary key of the workspace
     * @param projectId the primary key of the data project (optionally)
     * @param fileName the file name
     * @param validateFileExtension whether to validate the file's extension
     * @param bytes the files's data
     */
    public static void addFile(
        long workspaceId, long projectId, String fileName,
        boolean validateFileExtension, byte[] bytes)
        throws InternalServerErrorException {

        getStore().addFile(
            workspaceId, projectId, fileName, validateFileExtension, bytes);
    }

    /**
     * Adds a file based on a {@link File} object.
     *
     * @param workspaceId the primary key of the workspace
     * @param projectId the primary key of the data project (optionally)
     * @param fileName the file name
     * @param validateFileExtension whether to validate the file's extension
     * @param file Name the file name
     */
    public static void addFile(
        long workspaceId, long projectId, String fileName,
        boolean validateFileExtension, File file)
        throws InternalServerErrorException {

        getStore().addFile(
            workspaceId, projectId, fileName, validateFileExtension, file);
    }

    /**
     * Adds a file based on a {@link InputStream} object.
     *
     * @param workspaceId the primary key of the workspace
     * @param projectId the primary key of the data project (optionally)
     * @param fileName the file name
     * @param validateFileExtension whether to validate the file's extension
     * @param is the files's data
     */
    public static void addFile(
        long workspaceId, long projectId, String fileName,
        boolean validateFileExtension, InputStream is)
        throws InternalServerErrorException {

        getStore().addFile(
            workspaceId, projectId, fileName, validateFileExtension, is);
    }

    /**
     * Adds a file based on a byte array. Enforces validation of file's
     * extension.
     *
     * @param workspaceId the primary key of the workspace
     * @param projectId the primary key of the data project (optionally)
     * @param fileName the file name
     * @param bytes the files's data
     */
    public static void addFile(
        long workspaceId, long projectId, String fileName, byte[] bytes)
        throws SystemException {

        getStore().addFile(workspaceId, projectId, fileName, bytes);
    }

    /**
     * Adds a file based on a {@link File} object. Enforces validation of file's
     * extension.
     *
     * @param workspaceId the primary key of the workspace
     * @param projectId the primary key of the data project (optionally)
     * @param fileName the file name
     * @param file Name the file name
     */
    public static void addFile(
        long workspaceId, long projectId, String fileName, File file)
        throws SystemException {

        getStore().addFile(workspaceId, projectId, fileName, file);
    }

    /**
     * Adds a file based on an {@link InputStream} object. Enforces validation
     * of file's extension.
     *
     * @param workspaceId the primary key of the workspace
     * @param projectId the primary key of the data project (optionally)
     * @param fileName the file name
     * @param is the files's data
     */
    public static void addFile(
        long workspaceId, long projectId, String fileName, InputStream is)
        throws InternalServerErrorException {

        getStore().addFile(workspaceId, projectId, fileName, is);
    }

    /**
     * Ensures workspace's root directory exists.
     *
     * @param workspaceId the primary key of the workspace
     */
    public static void checkRoot(long workspaceId) {
        getStore().checkRoot(workspaceId);
    }

    /**
     * Creates a new copy of the file version.
     *
     * @param workspaceId the primary key of the workspace
     * @param projectId the primary key of the data project (optionally)
     * @param fileName the original's file name
     * @param fromVersionLabel the original file's version label
     * @param toVersionLabel the new version label
     */
    public static void copyFileVersion(
        long workspaceId, long projectId, String fileName,
        String fromVersionLabel, String toVersionLabel)
        throws InternalServerErrorException {

        getStore().copyFileVersion(
            workspaceId, projectId, fileName, fromVersionLabel,
            toVersionLabel);
    }

    /**
     * Deletes a directory.
     *
     * @param workspaceId the primary key of the workspace
     * @param projectId the primary key of the data project (optionally)
     * @param dirName the directory's name
     */
    public static void deleteDirectory(
        long workspaceId, long projectId, String dirName) {

        getStore().deleteDirectory(workspaceId, projectId, dirName);
    }

    /**
     * Deletes a file. If a file has multiple versions, all versions will be
     * deleted.
     *
     * @param workspaceId the primary key of the workspace
     * @param projectId the primary key of the data project (optionally)
     * @param fileName the file's name
     */
    public static void deleteFile(
        long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException {

        getStore().deleteFile(workspaceId, projectId, fileName);
    }

    /**
     * Deletes a file at a particular version.
     *
     * @param workspaceId the primary key of the workspace
     * @param projectId the primary key of the data project (optionally)
     * @param fileName the file's name
     * @param versionLabel the file's version label
     */
    public static void deleteFile(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException {

        getStore().deleteFile(workspaceId, projectId, fileName, versionLabel);
    }

    /**
     * Returns the file as a {@link File} object.
     *
     * <p>
     * This method is useful when optimizing low-level file operations like
     * copy. The client must not delete or change the returned {@link File}
     * object in any way. This method is only supported in certain stores. If
     * not supported, this method will throw an {@link
     * UnsupportedOperationException}.
     * </p>
     *
     * <p>
     * If using an S3 store, it is preferable for performance reasons to use
     * {@link #getFileAsStream(long, long, String)} instead of this method
     * wherever possible.
     * </p>
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project (optionally)
     * @param  fileName the file's name
     * @return Returns the {@link File} object with the file's name
     */
    public static File getFile(
        long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException {

        return getStore().getFile(workspaceId, projectId, fileName);
    }

    /**
     * Returns the file as a {@link File} object.
     *
     * <p>
     * This method is useful when optimizing low-level file operations like
     * copy. The client must not delete or change the returned {@link File}
     * object in any way. This method is only supported in certain stores. If
     * not supported, this method will throw an {@link
     * UnsupportedOperationException}.
     * </p>
     *
     * <p>
     * If using an S3 store, it is preferable for performance reasons to use
     * {@link #getFileAsStream(long, long, String, String)} instead of this
     * method wherever possible.
     * </p>
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project (optionally)
     * @param  fileName the file's name
     * @param  versionLabel the file's version label
     * @return Returns the {@link File} object with the file's name
     */
    public static File getFile(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException {

        return getStore().getFile(
            workspaceId, projectId, fileName, versionLabel);
    }

    /**
     * Returns the file as a byte array.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project (optionally)
     * @param  fileName the file's name
     * @return Returns the byte array with the file's name
     */
    public static byte[] getFileAsBytes(
        long workspaceId, long projectId, String fileName)
        throws SystemException {

        return getStore().getFileAsBytes(workspaceId, projectId, fileName);
    }

    /**
     * Returns the file as a byte array.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project (optionally)
     * @param  fileName the file's name
     * @param  versionLabel the file's version label
     * @return Returns the byte array with the file's name
     */
    public static byte[] getFileAsBytes(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException {

        return getStore().getFileAsBytes(
            workspaceId, projectId, fileName, versionLabel);
    }

    /**
     * Returns the file as an {@link InputStream} object.
     *
     * <p>
     * If using an S3 store, it is preferable for performance reasons to use
     * this method to get the file as an {@link InputStream} instead of using
     * other methods to get the file as a {@link File}.
     * </p>
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project (optionally)
     * @param  fileName the file's name
     * @return Returns the {@link InputStream} object with the file's name
     */
    public static InputStream getFileAsStream(
        long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException {

        return getStore().getFileAsStream(workspaceId, projectId, fileName);
    }

    /**
     * Returns the file as an {@link InputStream} object.
     *
     * <p>
     * If using an S3 store, it is preferable for performance reasons to use
     * this method to get the file as an {@link InputStream} instead of using
     * other methods to get the file as a {@link File}.
     * </p>
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project (optionally)
     * @param  fileName the file's name
     * @param  versionLabel the file's version label
     * @return Returns the {@link InputStream} object with the file's name
     */
    public static InputStream getFileAsStream(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException {

        return getStore().getFileAsStream(
            workspaceId, projectId, fileName, versionLabel);
    }

    /**
     * Returns all files of the directory.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project (optionally)
     * @param  dirName the directory's name
     * @return Returns all files of the directory
     */
    public static String[] getFileNames(
        long workspaceId, long projectId, String dirName)
        throws InternalServerErrorException {

        return getStore().getFileNames(workspaceId, projectId, dirName);
    }

    /**
     * Returns the size of the file.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project (optionally
     * @param  fileName the file's name
     * @return Returns the size of the file
     */
    public static long getFileSize(
        long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException {

        return getStore().getFileSize(workspaceId, projectId, fileName);
    }

    /**
     * Returns <code>true</code> if the directory exists.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project (optionally)
     * @param  dirName the directory's name
     * @return <code>true</code> if the directory exists; <code>false</code>
     *         otherwise
     */
    public static boolean hasDirectory(
        long workspaceId, long projectId, String dirName)
        throws InternalServerErrorException {

        return getStore().hasDirectory(workspaceId, projectId, dirName);
    }

    /**
     * Returns <code>true</code> if the file exists.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project (optionally)
     * @param  fileName the file's name
     * @return <code>true</code> if the file exists; <code>false</code>
     *         otherwise
     */
    public static boolean hasFile(
        long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException {

        return getStore().hasFile(workspaceId, projectId, fileName);
    }

    /**
     * Returns <code>true</code> if the file exists.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project (optionally)
     * @param  fileName the file's name
     * @param  versionLabel the file's version label
     * @return <code>true</code> if the file exists; <code>false</code>
     *         otherwise
     */
    public static boolean hasFile(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException {

        return getStore().hasFile(
            workspaceId, projectId, fileName, versionLabel);
    }

    public static boolean isValidName(String name) {
        return getStore().isValidName(name);
    }

    /**
     * Moves an existing directory.
     * @param srcDir the original directory's name
     * @param destDir the new directory's name
     */
    public static void move(String srcDir, String destDir) {
        getStore().move(srcDir, destDir);
    }

    /**
     * Moves a file to a new data project.
     *
     * @param workspaceId the primary key of the workspace
     * @param projectId the primary key of the data project
     * @param newprojectId the primary key of the new data project
     * @param fileName the file's name
     */
    public static void updateFile(
        long workspaceId, long projectId, long newprojectId,
        String fileName)
        throws InternalServerErrorException {

        getStore().updateFile(
            workspaceId, projectId, newprojectId, fileName);
    }

    /**
     * Update's the file's name
     *
     * @param workspaceId the primary key of the workspace
     * @param projectId the primary key of the data project (optionally)
     * @param fileName the file's name
     * @param newFileName the file's new name
     */
    public static void updateFile(
        long workspaceId, long projectId, String fileName,
        String newFileName)
        throws InternalServerErrorException {

        getStore().updateFile(workspaceId, projectId, fileName, newFileName);
    }

    /**
     * Updates a file based on a {@link File} object.
     *
     * @param workspaceId the primary key of the workspace
     * @param projectId the primary key of the data project (optionally))
     * @param fileName the file name
     * @param fileExtension the file's extension
     * @param validateFileExtension whether to validate the file's extension
     * @param versionLabel the file's new version label
     * @param sourceFileName the new file's original name
     * @param file Name the file name
     */
    public static void updateFile(
        long workspaceId, long projectId, String fileName,
        String fileExtension, boolean validateFileExtension,
        String versionLabel, String sourceFileName, File file)
        throws InternalServerErrorException {

        getStore().updateFile(
            workspaceId, projectId, fileName, fileExtension,
            validateFileExtension, versionLabel, sourceFileName, file);
    }

    /**
     * Updates a file based on a {@link InputStream} object.
     *
     * @param workspaceId the primary key of the workspace
     * @param projectId the primary key of the data project (optionally)
     * @param fileName the file name
     * @param fileExtension the file's extension
     * @param validateFileExtension whether to validate the file's extension
     * @param versionLabel the file's new version label
     * @param sourceFileName the new file's original name
     * @param is the new file's data
     */
    public static void updateFile(
        long workspaceId, long projectId, String fileName,
        String fileExtension, boolean validateFileExtension,
        String versionLabel, String sourceFileName, InputStream is)
        throws InternalServerErrorException {

        getStore().updateFile(
            workspaceId, projectId, fileName, fileExtension,
            validateFileExtension, versionLabel, sourceFileName, is);
    }

    /**
     * Update's a file version label. Similar to {@link #copyFileVersion(long,
     * long, String, String, String)} except that the old file version is
     * deleted.
     *
     * @param workspaceId the primary key of the workspace
     * @param projectId the primary key of the data project (optionally)
     * @param fileName the file's name
     * @param fromVersionLabel the file's version label
     * @param toVersionLabel the file's new version label
     */
    public static void updateFileVersion(
        long workspaceId, long projectId, String fileName,
        String fromVersionLabel, String toVersionLabel)
        throws InternalServerErrorException {

        getStore().updateFileVersion(
            workspaceId, projectId, fileName, fromVersionLabel,
            toVersionLabel);
    }

    /**
     * Validates a file's name.
     *
     * @param fileName the file's name
     * @param validateFileExtension whether to validate the file's extension
     */
    public static void validate(String fileName, boolean validateFileExtension)
        throws InternalServerErrorException {

        getStore().validate(fileName, validateFileExtension);
    }

    /**
     * Validates a file's name and data.
     *
     * @param fileName the file's name
     * @param validateFileExtension whether to validate the file's extension
     * @param bytes the file's data (optionally) <code>null</code>)
     */
    public static void validate(
        String fileName, boolean validateFileExtension, byte[] bytes)
        throws InternalServerErrorException {

        getStore().validate(fileName, validateFileExtension, bytes);
    }

    /**
     * Validates a file's name and data.
     *
     * @param fileName the file's name
     * @param validateFileExtension whether to validate the file's extension
     * @param file Name the file's name
     */
    public static void validate(
        String fileName, boolean validateFileExtension, File file)
        throws InternalServerErrorException {

        getStore().validate(fileName, validateFileExtension, file);
    }

    /**
     * Validates a file's name and data.
     *
     * @param fileName the file's name
     * @param validateFileExtension whether to validate the file's extension
     * @param is the file's data (optionally <code>null</code>)
     */
    public static void validate(
        String fileName, boolean validateFileExtension, InputStream is)
        throws InternalServerErrorException {

        getStore().validate(fileName, validateFileExtension, is);
    }

    public static void validate(
        String fileName, String fileExtension, String sourceFileName,
        boolean validateFileExtension)
        throws InternalServerErrorException {

        getStore().validate(
            fileName, fileExtension, sourceFileName, validateFileExtension);
    }

    /**
     * Validates a file's name and data.
     *
     * @param fileName the file's name
     * @param fileExtension the file's extension
     * @param sourceFileName the file's original name
     * @param validateFileExtension whether to validate the file's extension
     * @param file Name the file's name
     */
    public static void validate(
        String fileName, String fileExtension, String sourceFileName,
        boolean validateFileExtension, File file)
        throws InternalServerErrorException {

        getStore().validate(
            fileName, fileExtension, sourceFileName, validateFileExtension,
            file);
    }

    /**
     * Validates a file's name and data.
     *
     * @param fileName the file's name
     * @param fileExtension the file's extension
     * @param sourceFileName the file's original name
     * @param validateFileExtension whether to validate the file's extension
     * @param is the file's data (optionally <code>null</code>)
     */
    public static void validate(
        String fileName, String fileExtension, String sourceFileName,
        boolean validateFileExtension, InputStream is)
        throws InternalServerErrorException {

        getStore().validate(
            fileName, fileExtension, sourceFileName, validateFileExtension, is);
    }

    public static void validateDirectoryName(String directoryName)
        throws InternalServerErrorException {

        getStore().validateDirectoryName(directoryName);
    }

    /**
     * Returns the {@link Store} object. Used primarily by Spring and should
     * not be used by the client.
     *
     * @return Returns the {@link Store} object
     */
    public static Store getStore() {
        return _store;
    }


    /**
     * Set's the {@link Store} object. Used primarily by Spring and should not
     * be used by the client.
     *
     * @param store the {@link Store} object
     */
    public void setStore(Store store) {
        _store = store;
    }

    private static Store _store;

    @Autowired
    public StoreUtil(Store store){
        _store = store;
    }

}
