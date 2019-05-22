package com.lagab.cmanager.store;

import com.lagab.cmanager.config.StorageProperties;
import com.lagab.cmanager.web.rest.errors.SystemException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface Store {

    public static final String VERSION_DEFAULT = "1.0";

    public StorageProperties getConfig();

    public void setConfig(StorageProperties config);

    public String getPath(String path);
    public String getPath(String path,boolean isTmp);
    public String getTempPath(String path);

    public void addDirectory(String path, String dirName) throws SystemException;

    public void addFile(String path,String fileName, byte[] bytes) throws SystemException;
    public void addFile(String path,String fileName, File file) throws SystemException;
    public void addFile(String path,String fileName, InputStream is) throws SystemException;
    public void addFile(String path,String fileName, InputStream is,boolean relative) throws SystemException;

    public void copyFileVersion(String path, String fileName,String fromVersionLabel, String toVersionLabel);

    public void deleteDirectory(String path, String dirName) throws SystemException;

    public void deleteFile(String path, String fileName);

    public void deleteFile(String path, String fileName, String versionLabel);

    public File getFile(String path, String fileName);

    public File getFile(String path, String fileName,String versionLabel);

    public byte[] getFileAsBytes(String path, String fileName) throws SystemException;

    public byte[] getFileAsBytes(String path, String fileName, String versionLabel) throws SystemException;

    public InputStream getFileAsStream(String path, String fileName);

    public InputStream getFileAsStream(String path, String fileName, String versionLabel);

    public String[] getFileNames(String path, String dirName);

    public long getFileSize(String path, String fileName);

    public boolean hasDirectory(String path, String dirName);

    public boolean hasFile(String path, String fileName);

    public boolean hasFile(String path, String fileName, String versionLabel);

    public void move(String srcDir, String destDir);

    public void move(String srcDir, String destDir, String fileName) throws SystemException;

    public void updateFile(String path, String fileName, String versionLabel, byte[] bytes);

    public void updateFile(String path, String fileName, String versionLabel, File file);

    public void updateFile(String path, String fileName, String versionLabel, InputStream is);


}
