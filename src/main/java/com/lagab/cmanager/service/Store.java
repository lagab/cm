package com.lagab.cmanager.service;

import java.io.File;
import java.io.InputStream;

public interface Store {

    public static final String VERSION_DEFAULT = "1.0";

    public void addDirectory(String dirName);

    public void addFile(String path,String fileName, byte[] bytes);
    public void addFile(String path,String fileName, File file);
    public void addFile(String path,String fileName, InputStream is);

    public void addFile(String path,String fileName,
                        boolean validateFileExtension, byte[] bytes);
    public void addFile(String path,String fileName,
                        boolean validateFileExtension, File file);
    public void addFile(String path,String fileName,
                        boolean validateFileExtension, InputStream is);

    public void copyFileVersion(String path, String fileName,
        String fromVersionLabel, String toVersionLabel);

    public void deleteDirectory(String path, String dirName);

    public void deleteFile(String path, String fileName, String versionLabel);

    public File getFile(String path, String fileName);

    public File getFile(String path, String fileName,String versionLabel);

    public byte[] getFileAsBytes(String path, String fileName);

    public byte[] getFileAsBytes(String path, String fileName, String versionLabel);

    public InputStream getFileAsStream(String path, String fileName);

    public InputStream getFileAsStream(String path, String fileName, String versionLabel);

    public String[] getFileNames(String path, String dirName);

    public long getFileSize(String path, String fileName);

    public boolean hasDirectory(String path, String dirName);

    public boolean hasFile(String path, String fileName);

    public boolean hasFile(String path, String fileName, String versionLabel);

    public void move(String srcDir, String destDir);

    public void updateFile(String path, String fileName,
        String versionLabel, byte[] bytes);

    public void updateFile(String path, String fileName,
        String versionLabel, File file);

    public void updateFile(String path, String fileName,
        String versionLabel, InputStream is);

    public void validate(String fileName, boolean validateFileExtension);

    public void validate(
        String fileName, boolean validateFileExtension, byte[] bytes);

    public void validate(
        String fileName, boolean validateFileExtension, File file);

    public void validate(
        String fileName, boolean validateFileExtension, InputStream is);


}
