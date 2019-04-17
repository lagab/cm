package com.lagab.cmanager.service.impl;

import com.lagab.cmanager.service.Store;

import java.io.*;

public abstract class BaseStore implements Store{

    /**
     * Adds a directory.
     *
     * @param  path the path of directory
     * @param  dirName the directory's name
     * @throws InternalServerErrorException if the directory's information was invalid
     */
    @Override
    public abstract void addDirectory(String path, String dirName) throws SystemException;

    /**
     * Adds a file based on a byte array.
     *
     * @param  path the path of file
     * @param  fileName the file name
     * @param  bytes the files's data
     * @throws InternalServerErrorException if the file's information was invalid
     */
    @Override
    public void addFile(String path, String fileName, byte[] bytes) {

        File file = null;

        try {
            file = FileUtil.createTempFile(bytes);

            addFile(path, fileName, file);
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
     * @param  path the path of file
     * @param  fileName the file name
     * @param  file Name the file name
     * @throws InternalServerErrorException if the file's information was invalid
     */
    @Override
    public void addFile(String path, String fileName, File file) {

        InputStream is = null;

        try {
            is = new FileInputStream(file);

            addFile(path, fileName, is);
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
     * @param  path the path of file
     * @param  fileName the file name
     * @param  is the files's data
     * @throws InternalServerErrorException if the file's information was invalid
     */
    @Override
    public abstract void addFile(String path, String fileName, InputStream is);

    /**
     * Deletes a directory.
     *
     * @param  path the path of directory
     * @param  dirName the directory's name
     * @throws InternalServerErrorException if the directory's information was invalid
     */
    @Override
    public abstract void deleteDirectory(
        String path, String dirName);

    /**
     * Deletes a file. If a file has multiple versions, all versions will be
     * deleted.
     *
     * @param  path the path of file
     * @param  fileName the file's name
     * @throws InternalServerErrorException if the file's information was invalid
     */
    @Override
    public abstract void deleteFile(
        String path, String fileName);

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
     * @param  path the path of file
     * @param  fileName the file's name
     * @return Returns the {@link File} object with the file's name
     * @throws InternalServerErrorException if the file's information was invalid
     */
    @Override
    public File getFile(String path, String fileName) {

        return getFile(path, fileName, StringConstants.BLANK);
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
     * @param  path the path of file
     * @param  fileName the file's name
     * @param  versionLabel the file's version label
     * @return Returns the {@link File} object with the file's name
     * @throws InternalServerErrorException if the file's information was invalid
     */
    @Override
    public File getFile(
        String path, String fileName,
        String versionLabel) {

        throw new UnsupportedOperationException();
    }

    /**
     * Returns the file as a byte array.
     *
     * @param  path the path of file
     * @param  fileName the file's name
     * @return Returns the byte array with the file's name
     * @throws InternalServerErrorException if the file's information was invalid
     */
    @Override
    public byte[] getFileAsBytes(
        String path, String fileName){

        byte[] bytes = null;

        try {
            InputStream is = getFileAsStream(path, fileName);

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
     * @param  path the path of file
     * @param  fileName the file's name
     * @param  versionLabel the file's version label
     * @return Returns the byte array with the file's name
     * @throws InternalServerErrorException if the file's information was invalid
     */
    @Override
    public byte[] getFileAsBytes(
        String path, String fileName,
        String versionLabel) {

        byte[] bytes = null;

        try {
            InputStream is = getFileAsStream(
                path, fileName, versionLabel);

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
     * @param  path the path of file
     * @param  fileName the file's name
     * @return Returns the {@link InputStream} object with the file's name
     * @throws InternalServerErrorException if the file's information was invalid
     */
    @Override
    public InputStream getFileAsStream(String path, String fileName){

        return getFileAsStream(path, fileName, StringConstants.BLANK);
    }

    /**
     * Returns the file as an {@link InputStream} object.
     *
     * @param  path the path of file
     * @param  fileName the file's name
     * @param  versionLabel the file's version label
     * @return Returns the {@link InputStream} object with the file's name
     * @throws InternalServerErrorException if the file's information was invalid
     */
    @Override
    public abstract InputStream getFileAsStream(
        String path, String fileName,
        String versionLabel);

    /**
     * Returns all files of the directory.
     *
     * @param  path the path of file
     * @param  dirName the directory's name
     * @return Returns all files of the directory
     * @throws InternalServerErrorException if the directory's information was invalid
     */
    @Override
    public abstract String[] getFileNames(
        String path, String dirName);

    /**
     * Returns the size of the file.
     *
     * @param  path the path of file
     * @param  fileName the file's name
     * @return Returns the size of the file
     * @throws InternalServerErrorException if the file's information was invalid
     */
    @Override
    public abstract long getFileSize(
        String path, String fileName);

    /**
     * Returns <code>true</code> if the directory exists.
     *
     * @param  path the path of file
     * @param  dirName the directory's name
     * @return <code>true</code> if the directory exists; <code>false</code>
     *         otherwise
     * @throws InternalServerErrorException if the directory's information was invalid
     */
    @Override
    public abstract boolean hasDirectory(
        String path, String dirName);

    /**
     * Returns <code>true</code> if the file exists.
     *
     * @param  path the path of file
     * @param  fileName the file's name
     * @return <code>true</code> if the file exists; <code>false</code>
     *         otherwise
     * @throws InternalServerErrorException if the file's information was invalid
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
     * @return <code>true</code> if the file exists; <code>false</code>
     *         otherwise
     * @throws InternalServerErrorException if the file's information was invalid
     */
    @Override
    public abstract boolean hasFile(
        String path, String fileName,
        String versionLabel);

    /**
     * Moves an existing directory
     * @param  srcDir the original directory's name
     * @param  destDir the new directory's name
     */
    @Override
    public void move(String srcDir, String destDir){

    }

}
