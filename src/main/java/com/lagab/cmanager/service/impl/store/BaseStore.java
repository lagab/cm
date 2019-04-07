package com.lagab.cmanager.service.impl.store;

import com.lagab.cmanager.service.Store;
import com.lagab.cmanager.web.rest.errors.*;
import com.lagab.cmanager.web.rest.util.FileUtil;
import com.lagab.cmanager.web.rest.util.StringConstants;
import com.lagab.cmanager.web.rest.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author GABRIEL
 * @since 05/04/2019.
 */
public abstract class BaseStore implements Store {
    /**
     * Adds a directory.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  dirName the directory's name
     * @throws InternalServerErrorException if the directory's information was invalid

     */
    @Override
    public abstract void addDirectory(
        long workspaceId, long projectId, String dirName) throws SystemException;

    /**
     * Adds a file based on a byte array.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the file name
     * @param  bytes the files's data
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public void addFile(
        long workspaceId, long projectId, String fileName, byte[] bytes)
        throws SystemException {

        File file = null;

        try {
            file = FileUtil.createTempFile(bytes);

            addFile(workspaceId, projectId, fileName, file);
        }
        catch (IOException ioe) {
            throw new SystemException("Unable to write temporary file", ioe);
        }
        finally {
            FileUtil.delete(file);
        }
    }

    /**
     * Adds a file based on a {@link File} object.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the file name
     * @param  file Name the file name
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public void addFile(
        long workspaceId, long projectId, String fileName, File file)
        throws SystemException {

        InputStream is = null;

        try {
            is = new FileInputStream(file);

            addFile(workspaceId, projectId, fileName, is);
        }
        catch (FileNotFoundException fnfe) {
            throw new NoSuchFileException(fileName);
        }
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            }
            catch (IOException ioe) {
                throw new SystemException("Unable to read bytes", ioe);
            }
        }
    }

    /**
     * Adds a file based on an {@link InputStream} object.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the file name
     * @param  is the files's data
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public abstract void addFile(
        long workspaceId, long projectId, String fileName, InputStream is)
        throws InternalServerErrorException, SystemException;

    /**
     * Ensures workspace's root directory exists.
     *
     * @param  workspaceId the primary key of the workspace

     */
    @Override
    public abstract void checkRoot(long workspaceId) throws InternalServerErrorException;

    /**
     * Creates a new copy of the file version.
     *
     * <p>
     * This method should be overrided if a more optimized approach can be used
     * (e.g., {@link FileSystemStore#copyFileVersion(long, long, String, String,
     * String)}).
     * </p>
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the original's file name
     * @param  fromVersionLabel the original file's version label
     * @param  toVersionLabel the new version label
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public void copyFileVersion(
        long workspaceId, long projectId, String fileName,
        String fromVersionLabel, String toVersionLabel)
        throws InternalServerErrorException, SystemException {

        if (fromVersionLabel.equals(toVersionLabel)) {
            throw new DuplicateFileException(
                String.format(
                    "{workspaceId=%s, fileName=%s, projectId=%s, version=%s}",
                    workspaceId, fileName, projectId, toVersionLabel));
        }

        InputStream is = getFileAsStream(
            workspaceId, projectId, fileName, fromVersionLabel);

        if (is == null) {
            //is = new UnsyncByteArrayInputStream(new byte[0]);
            return;
        }

        updateFile(workspaceId, projectId, fileName, toVersionLabel, is);
    }

    /**
     * Deletes a directory.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  dirName the directory's name
     * @throws InternalServerErrorException if the directory's information was invalid

     */
    @Override
    public abstract void deleteDirectory(
        long workspaceId, long projectId, String dirName)
        throws InternalServerErrorException, SystemException;

    /**
     * Deletes a file. If a file has multiple versions, all versions will be
     * deleted.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the file's name
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public abstract void deleteFile(
        long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException, SystemException;

    /**
     * Deletes a file at a particular version.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the file's name
     * @param  versionLabel the file's version label
     * @throws InternalServerErrorException if the file's information was invalid
     */
    @Override
    public abstract void deleteFile(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException, SystemException;

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
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the file's name
     * @return Returns the {@link File} object with the file's name
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public File getFile(long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException, SystemException {

        return getFile(workspaceId, projectId, fileName, StringConstants.BLANK);
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
     * This method should be overrided if a more optimized approach can be used
     * (e.g., {@link FileSystemStore#getFile(long, long, String, String)}).
     * </p>
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the file's name
     * @param  versionLabel the file's version label
     * @return Returns the {@link File} object with the file's name
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public File getFile(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException, SystemException {

        throw new UnsupportedOperationException();
    }

    /**
     * Returns the file as a byte array.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the file's name
     * @return Returns the byte array with the file's name
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public byte[] getFileAsBytes(
        long workspaceId, long projectId, String fileName)
        throws SystemException {

        byte[] bytes = null;

        try {
            InputStream is = getFileAsStream(workspaceId, projectId, fileName);

            bytes = FileUtil.getBytes(is);
        }
        catch (IOException ioe) {
            throw new SystemException(ioe);
        }

        return bytes;
    }

    /**
     * Returns the file as a byte array.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the file's name
     * @param  versionLabel the file's version label
     * @return Returns the byte array with the file's name
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public byte[] getFileAsBytes(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException, SystemException {

        byte[] bytes = null;

        try {
            InputStream is = getFileAsStream(
                workspaceId, projectId, fileName, versionLabel);

            bytes = FileUtil.getBytes(is);
        }
        catch (IOException ioe) {
            throw new InternalServerErrorException(ioe);
        }

        return bytes;
    }

    /**
     * Returns the file as an {@link InputStream} object.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the file's name
     * @return Returns the {@link InputStream} object with the file's name
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public InputStream getFileAsStream(
        long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException, SystemException {

        return getFileAsStream(
            workspaceId, projectId, fileName, StringConstants.BLANK);
    }

    /**
     * Returns the file as an {@link InputStream} object.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the file's name
     * @param  versionLabel the file's version label
     * @return Returns the {@link InputStream} object with the file's name
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public abstract InputStream getFileAsStream(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException, SystemException;

    /**
     * Returns all files of the directory.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  dirName the directory's name
     * @return Returns all files of the directory
     * @throws InternalServerErrorException if the directory's information was invalid

     */
    @Override
    public abstract String[] getFileNames(
        long workspaceId, long projectId, String dirName)
        throws InternalServerErrorException, SystemException;

    /**
     * Returns the size of the file.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the file's name
     * @return Returns the size of the file
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public abstract long getFileSize(
        long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException, SystemException;

    /**
     * Returns <code>true</code> if the directory exists.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  dirName the directory's name
     * @return <code>true</code> if the directory exists; <code>false</code>
     *         otherwise
     * @throws InternalServerErrorException if the directory's information was invalid

     */
    @Override
    public abstract boolean hasDirectory(
        long workspaceId, long projectId, String dirName)
        throws InternalServerErrorException, SystemException;

    /**
     * Returns <code>true</code> if the file exists.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the file's name
     * @return <code>true</code> if the file exists; <code>false</code>
     *         otherwise
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public boolean hasFile(long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException {

        return hasFile(workspaceId, projectId, fileName, VERSION_DEFAULT);
    }

    /**
     * Returns <code>true</code> if the file exists.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the file's name
     * @param  versionLabel the file's version label
     * @return <code>true</code> if the file exists; <code>false</code>
     *         otherwise
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public abstract boolean hasFile(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException;

    /**
     * Moves an existing directory
     * @param  srcDir the original directory's name
     * @param  destDir the new directory's name

     */
    @Override
    public void move(String srcDir, String destDir){

    }

    /**
     * Moves a file to a new data project.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  newprojectId the primary key of the new data project
     * @param  fileName the file's name
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public abstract void updateFile(
        long workspaceId, long projectId, long newprojectId,
        String fileName)
        throws InternalServerErrorException, SystemException;

    /**
     * Updates a file based on a byte array.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the file name
     * @param  versionLabel the file's new version label
     * @param  bytes the new file's data
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public void updateFile(
        long workspaceId, long projectId, String fileName,
        String versionLabel, byte[] bytes)
        throws SystemException {

        File file = null;

        try {
            file = FileUtil.createTempFile(bytes);

            updateFile(workspaceId, projectId, fileName, versionLabel, file);
        }
        catch (IOException ioe) {
            throw new SystemException("Unable to write temporary file", ioe);
        }
        finally {
            FileUtil.delete(file);
        }
    }

    /**
     * Updates a file based on a {@link File} object.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the file name
     * @param  versionLabel the file's new version label
     * @param  file Name the file name
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public void updateFile(
        long workspaceId, long projectId, String fileName,
        String versionLabel, File file)
        throws InternalServerErrorException, SystemException {

        InputStream is = null;

        try {
            is = new FileInputStream(file);

            updateFile(workspaceId, projectId, fileName, versionLabel, is);
        }
        catch (FileNotFoundException fnfe) {
            throw new NoSuchFileException(fileName);
        }
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            }
            catch (IOException ioe) {
                _log.error(ioe.toString());
            }
        }
    }

    /**
     * Updates a file based on an {@link InputStream} object.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the file name
     * @param  versionLabel the file's new version label
     * @param  is the new file's data
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public abstract void updateFile(
        long workspaceId, long projectId, String fileName,
        String versionLabel, InputStream is)
        throws InternalServerErrorException, SystemException;

    /**
     * Update's a file version label. Similar to {@link #copyFileVersion(long,
     * long, String, String, String)} except that the old file version is
     * deleted.
     *
     * @param  workspaceId the primary key of the workspace
     * @param  projectId the primary key of the data project
     * @param  fileName the file's name
     * @param  fromVersionLabel the file's version label
     * @param  toVersionLabel the file's new version label
     * @throws InternalServerErrorException if the file's information was invalid

     */
    @Override
    public void updateFileVersion(
        long workspaceId, long projectId, String fileName,
        String fromVersionLabel, String toVersionLabel)
        throws InternalServerErrorException, SystemException {

        InputStream is = getFileAsStream(
            workspaceId, projectId, fileName, fromVersionLabel);

        if (is == null) {
            //is = new UnsyncByteArrayInputStream(new byte[0]);
            return;
        }

        updateFile(workspaceId, projectId, fileName, toVersionLabel, is);

        deleteFile(workspaceId, projectId, fileName, fromVersionLabel);
    }

    @Override
    public void validate(String fileName, boolean validateFileExtension)
        throws InternalServerErrorException, FileNameException, FileExtensionException {

        Validator.validateFileName(fileName);

        if (validateFileExtension) {
            Validator.validateFileExtension(fileName);
        }
    }

    @Override
    public void validate(
        String fileName, boolean validateFileExtension, byte[] bytes)
        throws InternalServerErrorException, FileSizeException, FileNameException, FileExtensionException {

        validate(fileName, validateFileExtension);

        Validator.validateFileSize(fileName, bytes);
    }

    @Override
    public void validate(
        String fileName, boolean validateFileExtension, File file)
        throws InternalServerErrorException, FileSizeException, FileNameException, FileExtensionException {

        validate(fileName, validateFileExtension);

        Validator.validateFileSize(fileName, file);
    }

    @Override
    public void validate(
        String fileName, boolean validateFileExtension, InputStream is)
        throws InternalServerErrorException, FileSizeException, FileNameException, FileExtensionException {

        validate(fileName, validateFileExtension);

        Validator.validateFileSize(fileName, is);
    }

    protected void logFailedDeletion(
        long companyId, long repositoryId, String fileName) {

        logFailedDeletion(companyId, repositoryId, fileName, null, null);
    }

    protected void logFailedDeletion(
        long companyId, long repositoryId, String fileName,
        Exception exception) {

        logFailedDeletion(companyId, repositoryId, fileName, null, exception);
    }

    protected void logFailedDeletion(
        long companyId, long repositoryId, String fileName,
        String versionLabel) {

        logFailedDeletion(
            companyId, repositoryId, fileName, versionLabel, null);
    }

    protected void logFailedDeletion(
        long companyId, long repositoryId, String fileName, String versionLabel,
        Exception cause) {

        if ((_log.isWarnEnabled() && (cause != null)) ||
            (_log.isDebugEnabled() && (cause == null))) {

            StringBuilder sb = new StringBuilder(9);

            sb.append("Unable to delete file {companyId=");
            sb.append(companyId);
            sb.append(", repositoryId=");
            sb.append(repositoryId);
            sb.append(", fileName=");
            sb.append(fileName);

            if (Validator.isNotNull(versionLabel)) {
                sb.append(", versionLabel=");
                sb.append(versionLabel);
            }

            sb.append("} because it does not exist");

            if (_log.isWarnEnabled() && (cause != null)) {
                _log.warn(sb.toString(), cause);
            }

            if (_log.isDebugEnabled() && (cause == null)) {
                _log.debug(sb.toString());
            }
        }
    }

    private static Logger _log = LoggerFactory.getLogger(BaseStore.class);
}
