package com.lagab.cmanager.store.impl;

import com.lagab.cmanager.web.rest.errors.SystemException;
import com.lagab.cmanager.web.rest.util.StringConstants;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author gabriel
 * @since 19/04/2019.
 */
// Todo: implement all methods
public class FileSystemStore extends BaseStore {

    protected File getFileNameDir(String path, String fileName) {
        File fileNameDir = new File(path + StringConstants.SLASH + fileName);
        return fileNameDir;
    }


    protected File getDirNameDir(String path, String dirName) {
        return getFileNameDir(path, dirName);
    }

    protected File getDirNameDir(long workspaceId, long projectId, String dirName) {
        File repositoryDir = getRepositoryDir(workspaceId, projectId);
        return getFileNameDir(repositoryDir.getPath() , dirName);
    }

    protected File getRepositoryDir(long workspaceId, long repositoryId) {
        File repositoryDir = new File(workspaceId + StringConstants.SLASH + repositoryId);
        return repositoryDir;
    }

    @Override
    public void addDirectory(String path, String dirName) throws SystemException {

        File dirNameDir = getDirNameDir(path, dirName);

        if (dirNameDir.exists()) {
            return;
        }
        try {
            FileUtils.forceMkdir(dirNameDir);
        }
        catch (IOException ioe) {
            throw new SystemException(ioe);
        }
    }

    @Override
    public void addFile(String path, String fileName, InputStream is) {

    }

    @Override
    public void addFile(String path, String fileName, boolean validateFileExtension, byte[] bytes) {

    }

    @Override
    public void addFile(String path, String fileName, boolean validateFileExtension, File file) {

    }

    @Override
    public void addFile(String path, String fileName, boolean validateFileExtension, InputStream is) {

    }

    @Override
    public void copyFileVersion(String path, String fileName, String fromVersionLabel, String toVersionLabel) {

    }

    @Override
    public void deleteDirectory(String path, String dirName) {

    }

    @Override
    public void deleteFile(String path, String fileName) {

    }

    @Override
    public void deleteFile(String path, String fileName, String versionLabel) {

    }

    @Override
    public InputStream getFileAsStream(String path, String fileName, String versionLabel) {
        return null;
    }

    @Override
    public String[] getFileNames(String path, String dirName) {
        return new String[0];
    }

    @Override
    public long getFileSize(String path, String fileName) {
        return 0;
    }

    @Override
    public boolean hasDirectory(String path, String dirName) {
        return false;
    }

    @Override
    public boolean hasFile(String path, String fileName, String versionLabel) {
        return false;
    }

    @Override
    public void updateFile(String path, String fileName, String versionLabel, byte[] bytes) {

    }

    @Override
    public void updateFile(String path, String fileName, String versionLabel, File file) {

    }

    @Override
    public void updateFile(String path, String fileName, String versionLabel, InputStream is) {

    }

    @Override
    public void validate(String fileName, boolean validateFileExtension) {

    }

    @Override
    public void validate(String fileName, boolean validateFileExtension, byte[] bytes) {

    }

    @Override
    public void validate(String fileName, boolean validateFileExtension, File file) {

    }

    @Override
    public void validate(String fileName, boolean validateFileExtension, InputStream is) {

    }
}
