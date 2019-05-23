package com.lagab.cmanager.store.impl;

import com.lagab.cmanager.config.StorageProperties;
import com.lagab.cmanager.web.rest.errors.SystemException;
import com.lagab.cmanager.web.rest.util.StringConstants;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author gabriel
 * @since 19/04/2019.
 */
// Todo: implement all methods
public class FileSystemStore extends BaseStore {

    public FileSystemStore(StorageProperties storeConfig) {
        super(storeConfig);
    }


    public String getPath(String path){
        return getPath(path,true);
    }
    public String getPath(String path, boolean relative){
        if( relative ){
            return getConfig().getUploadDir()+ StringConstants.SLASH + path;
        }
        return path;
    }

    protected File getDirNameDir(String path, String dirName) {
        return new File(getFileNameDir(path,dirName));
    }

    protected File getDirNameDir(long workspaceId, long projectId, String dirName) {
        File repositoryDir = getRepositoryDir(workspaceId, projectId);
        return getDirNameDir(repositoryDir.getPath() , dirName);
    }

    protected File getRepositoryDir(long workspaceId, long repositoryId) {
        return new File(workspaceId + StringConstants.SLASH + repositoryId);
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
    public void copyFileVersion(String path, String fileName, String fromVersionLabel, String toVersionLabel) {
        log.error("Unsupported Operation");
    }

    @Override
    public void deleteDirectory(String path, String dirName) throws SystemException {
        String completePath = path + StringConstants.SLASH + dirName;
        if( Files.isDirectory(Paths.get(completePath))){
            try {
                FileUtils.deleteDirectory(new File(completePath));
            } catch (IOException e) {
                throw new SystemException(e);
            }
        }
    }

    @Override
    public void deleteFile(String path, String fileName) throws SystemException{
        deleteFile(path,fileName,true);
    }

    public void deleteFile(String path, String fileName, boolean relative) throws SystemException{
        File targetFile = new File(getPath(path,relative) + StringConstants.SLASH + fileName);
        FileUtils.deleteQuietly(targetFile);
        if(isEmptyDirectory( getPath(path, relative) )) {
            try {
                FileUtils.deleteDirectory(new File(getPath(path, relative)));
            } catch (IOException e) {
                throw new SystemException(e);
            }
        }
    }

    @Override
    public void deleteFile(String path, String fileName, String versionLabel) throws SystemException {
        deleteFile(path,getVersionFileName(fileName,versionLabel),true);
    }

    @Override
    public InputStream getFileAsStream(String path, String fileName, String versionLabel) throws SystemException{
        File file = new File(getFileNameDir(getPath(path),getVersionFileName(fileName,versionLabel)));
        try {
            return  FileUtils.openInputStream(file);
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }

    @Override
    public String[] getFileNames(String dirPath) {
        File[] files = Paths.get(dirPath).toFile().listFiles();
        if( files == null || files.length == 0){
            return new String[0];
        }
        return Arrays.stream(files).map(File::getName).toArray(String[]::new);
    }

    @Override
    public long getFileSize(String path, String fileName) {
        return FileUtils.sizeOf(getDirNameDir(path,fileName));
    }

    @Override
    public boolean hasDirectory(String path, String dirName) {
        return Files.isDirectory(Paths.get(getFileNameDir(path,dirName)));
    }

    @Override
    public boolean hasFile(String path, String fileName, String versionLabel) {
        String versionFileName = getVersionFileName(fileName,versionLabel);
        return Files.isRegularFile(Paths.get(getFileNameDir(path,versionFileName)));
    }

    @Override
    public void updateFile(String path, String fileName, String versionLabel, byte[] bytes) throws SystemException {
        updateFile(path,fileName,versionLabel,new ByteArrayInputStream(bytes));
    }

    @Override
    public void updateFile(String path, String fileName, String versionLabel, File file) throws SystemException {
        try {
            updateFile(path,fileName,versionLabel, FileUtils.openInputStream(file));
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }

    @Override
    public void updateFile(String path, String fileName, String versionLabel, InputStream is) throws SystemException{
        String versionFileName = getVersionFileName(fileName,versionLabel);
        updateFile(path,versionFileName,is);
    }

    @Override
    public void updateFile(String path, String fileName, InputStream is) throws SystemException{
        File file = getDirNameDir(getPath(path),fileName);
        try {
            FileUtils.copyInputStreamToFile(is,file);
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }

    @Override
    public void move(String srcDir, String destDir) throws SystemException {
        File sourceFile = new File(getPath(srcDir));
        File targetFile = new File(getPath(destDir));
        try {
            FileUtils.moveDirectory(sourceFile,targetFile);
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }


    public void move(String srcDir, String destDir,String fileName) throws SystemException {
        File sourceFile = new File(getPath(srcDir,false) + StringConstants.SLASH + fileName);
        File targetFile = new File(getPath(destDir,false));
        try {
            FileUtils.moveFileToDirectory(sourceFile,targetFile,true);
            if(isEmptyDirectory( getPath(srcDir, false) )) {
                FileUtils.deleteDirectory(new File(getPath(srcDir, false)));
            }
        } catch (IOException e) {
            throw new SystemException(e);
        }

    }

    public static boolean isEmptyDirectory(String dirName){
        return (!Files.isDirectory(Paths.get(dirName))) || Paths.get(dirName).toFile()== null ||  Paths.get(dirName).toFile().listFiles().length == 0;
    }
}
