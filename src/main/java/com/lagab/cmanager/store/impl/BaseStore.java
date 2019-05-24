package com.lagab.cmanager.store.impl;

import com.lagab.cmanager.config.StorageProperties;
import com.lagab.cmanager.store.Store;
import com.lagab.cmanager.web.rest.errors.SystemException;
import com.lagab.cmanager.store.errors.NoSuchFileException;
import com.lagab.cmanager.web.rest.util.StringConstants;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public abstract class BaseStore implements Store{

    protected final Logger log = LoggerFactory.getLogger(BaseStore.class);
    protected StorageProperties config;

    public BaseStore(StorageProperties storeConfig){
        this.config = storeConfig;
    }

    public StorageProperties getConfig() {
        return config;
    }

    public void setConfig(StorageProperties config) {
        this.config = config;
    }

    @Override
    public String getPath(String path){
        return getPath(path,true);
    }

    @Override
    public String getTempPath(String path){
        return config.getTmpDir() + StringConstants.SLASH + path;
    }

    public String getFileNameDir(String path, String fileName){
        return path + StringConstants.SLASH + fileName;
    }

    /**
     * Adds a directory.
     *
     * @param  path the path of directory
     * @param  dirName the directory's name
     * @throws SystemException if the directory's information was invalid
     */
    @Override
    public abstract void addDirectory(String path, String dirName) throws SystemException;

    /**
     * Adds a file based on a byte array.
     *
     * @param  path the path of file
     * @param  fileName the file name
     * @param  bytes the files's data
     * @throws SystemException if the file's information was invalid
     */
    @Override
    public void addFile(String path, String fileName, byte[] bytes) throws SystemException {
        addFile(path, fileName, new ByteArrayInputStream(bytes));
    }

    /**
     * Adds a file based on a {@link File} object.
     *
     * @param  path the path of file
     * @param  fileName the file name
     * @param  file Name the file name
     * @throws SystemException if the file's information was invalid
     */
    @Override
    public void addFile(String path, String fileName, File file) throws SystemException {

        InputStream is;

        try {
            is = new FileInputStream(file);
            addFile(path, fileName, is);
        }
        catch (FileNotFoundException fnfe) {
            throw new NoSuchFileException(fileName,fnfe);
        }

    }

    /**
     * Adds a file based on an {@link InputStream} object.
     *
     * @param  path the path of file
     * @param  fileName the file name
     * @param  is the files's data
     * @throws SystemException if the file's information was invalid
     */
    @Override
    public abstract void addFile(String path, String fileName, InputStream is) throws SystemException;

    /**
     * Deletes a directory.
     *
     * @param  path the path of directory
     * @param  dirName the directory's name
     * @throws SystemException if the directory's information was invalid
     */
    @Override
    public abstract void deleteDirectory(String path, String dirName) throws SystemException;

    /**
     * Deletes a file. If a file has multiple versions, all versions will be
     * deleted.
     *
     * @param  path the path of file
     * @param  fileName the file's name
     * @throws SystemException if the file's information was invalid
     */
    @Override
    public abstract void deleteFile(String path, String fileName) throws SystemException;

    /**
     * Returns the file as a {@link File} object.
     *
     * <p>
     * This method is useful when optimizing low-level file operations like
     * copy. The client must not delete or change the returned {@link File}
     * object in any way. This method is only supported in certain stores. If
     * not supported, this method will throw an {@link UnsupportedOperationException}.
     * </p>
     *
     * @param  path the path of file
     * @param  fileName the file's name
     * @return Returns the {@link File} object with the file's name
     * @throws SystemException if the file's information was invalid
     */
    @Override
    public File getFile(String path, String fileName) throws SystemException{
        return getFile(path, fileName, StringConstants.BLANK);
    }

    /**
     * Returns the file as a {@link File} object.
     *
     * <p>
     * This method is useful when optimizing low-level file operations like
     * copy. The client must not delete or change the returned {@link File}
     * object in any way. This method is only supported in certain stores. If
     * not supported, this method will throw an {@link UnsupportedOperationException}.
     * </p>
     *
     * @param  path the path of file
     * @param  fileName the file's name
     * @param  versionLabel the file's version label
     * @return Returns the {@link File} object with the file's name
     * @throws SystemException if the file's information was invalid
     */
    @Override
    public File getFile(String path, String fileName, String versionLabel)  throws SystemException{
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the file as a byte array.
     *
     * @param  path the path of file
     * @param  fileName the file's name
     * @return Returns the byte array with the file's name
     * @throws SystemException if the file's information was invalid
     */
    @Override
    public byte[] getFileAsBytes(String path, String fileName) throws SystemException {

        byte[] bytes;

        try {
            InputStream is = getFileAsStream(path, fileName);
            bytes = IOUtils.toByteArray(is);
        }
        catch (IOException ioe) {
            throw new SystemException(ioe);
        }

        return bytes;
    }

    /**
     * Returns the file as a byte array.
     *
     * @param  path the path of file
     * @param  fileName the file's name
     * @param  versionLabel the file's version label
     * @return Returns the byte array with the file's name
     * @throws SystemException if the file's information was invalid
     */
    @Override
    public byte[] getFileAsBytes(String path, String fileName, String versionLabel) throws SystemException {

        byte[] bytes;

        try {
            InputStream is = getFileAsStream(path, fileName, versionLabel);
            bytes = IOUtils.toByteArray(is);
        }
        catch (IOException ioe) {
            throw new SystemException(ioe);
        }
        return bytes;
    }

    /**
     * Returns the file as an {@link InputStream} object.
     *
     * @param  path the path of file
     * @param  fileName the file's name
     * @return Returns the {@link InputStream} object with the file's name
     * @throws SystemException if the file's information was invalid
     */
    @Override
    public InputStream getFileAsStream(String path, String fileName) throws SystemException{
        return getFileAsStream(path, fileName, StringConstants.BLANK);
    }

    /**
     * Returns the file as an {@link InputStream} object.
     *
     * @param  path the path of file
     * @param  fileName the file's name
     * @param  versionLabel the file's version label
     * @return Returns the {@link InputStream} object with the file's name
     * @throws SystemException if the file's information was invalid
     */
    @Override
    public abstract InputStream getFileAsStream(String path, String fileName, String versionLabel) throws SystemException;


    /**
     * Returns all files of the directory.
     *
     * @param  path the path of file
     * @param  dirName the directory's name
     * @return Returns all files of the directory
     */
    @Override
    public String[] getFileNames(String path, String dirName) {
        return getFileNames( getFileNameDir(path,dirName) );
    }
    /**
     * Returns all files of the directory.
     *
     * @param  dirPath the directory's path
     * @return Returns all files of the directory
     */
    @Override
    public abstract String[] getFileNames(String dirPath);

    /**
     * Returns the size of the file.
     *
     * @param  path the path of file
     * @param  fileName the file's name
     * @return Returns the size of the file
     */
    @Override
    public abstract long getFileSize(String path, String fileName);

    /**
     * Returns <code>true</code> if the directory exists.
     *
     * @param  path the path of file
     * @param  dirName the directory's name
     * @return <code>true</code> if the directory exists; <code>false</code> otherwise
     */
    @Override
    public abstract boolean hasDirectory(String path, String dirName);


    public String getVersionFileName(String fileName, String versionLabel){
        if(versionLabel.equals(StringConstants.BLANK)){
            return fileName;
        }
        String basename = FilenameUtils.getBaseName(fileName);
        String extension = FilenameUtils.getExtension(fileName);
        return basename + StringConstants.DASH + versionLabel + StringConstants.PERIOD + extension;
    }
    /**
     * Returns <code>true</code> if the file exists.
     *
     * @param  path the path of file
     * @param  fileName the file's name
     * @return <code>true</code> if the file exists; <code>false</code> otherwise
     */
    @Override
    public boolean hasFile(String path, String fileName){
        return hasFile(path, fileName, VERSION_DEFAULT);
    }

    /**
     * Returns <code>true</code> if the file exists.
     *
     * @param  path the path of file
     * @param  fileName the file's name
     * @param  versionLabel the file's version label
     * @return <code>true</code> if the file exists; <code>false</code> otherwise
     */
    @Override
    public abstract boolean hasFile(String path, String fileName,String versionLabel);

    /**
     * Moves an existing directory
     * @param  srcDir the original directory's name
     * @param  destDir the new directory's name
     */
    @Override
    public void move(String srcDir, String destDir) throws SystemException {

    }

}
