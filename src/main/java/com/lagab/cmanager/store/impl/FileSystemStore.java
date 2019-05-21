package com.lagab.cmanager.store.impl;

import com.lagab.cmanager.config.StorageProperties;
import com.lagab.cmanager.store.util.FileUtil;
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

    public FileSystemStore(StorageProperties storeConfig) {
        super(storeConfig);
    }

    protected File getFileNameDir(String path, String fileName) {
        File fileNameDir = new File(  path + StringConstants.SLASH + fileName);
        return fileNameDir;
    }

    public String getPath(String path){
        return getPath(path,true);
    }
    public String getPath(String path, boolean relative){
        if( relative){
            return getConfig().getUploadDir()+ StringConstants.SLASH + path;
        }
        return path;
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
    public void addFile(String path, String fileName, InputStream is) throws SystemException {
        addFile(path,fileName,is,true);
    }

    @Override
    public void addFile(String path, String fileName, InputStream is,boolean relative) throws SystemException {
        File targetFile = new File(getPath(path,relative) + StringConstants.SLASH + fileName);
        try {
            FileUtils.copyInputStreamToFile(is, targetFile);
        } catch (IOException e) {
            throw new SystemException(e);
        }
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

    @Override
    public void move(String srcDir, String destDir){
    }


    public void move(String srcDir, String destDir,String fileName) throws SystemException {
        File sourceFile = new File(getPath(srcDir,false) + StringConstants.SLASH + fileName);
        File targetFile = new File(getPath(destDir,false));
        try {
            FileUtils.moveFileToDirectory(sourceFile,targetFile,true);
            FileUtils.deleteDirectory(new File(getPath(srcDir,false)));
        } catch (IOException e) {
            throw new SystemException(e);
        }

    }
}
