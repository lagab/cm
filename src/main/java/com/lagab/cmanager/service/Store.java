package com.lagab.cmanager.service;

import com.lagab.cmanager.web.rest.errors.InternalServerErrorException;

import java.io.File;
import java.io.InputStream;

/**
 * @author GABRIEL
 * @since 05/04/2019.
 */
public interface Store {

    public void addDirectory(long workspaceId, long projectId, String dirName)
        throws InternalServerErrorException;

    public void addFile(
        long workspaceId, long projectId, String fileName,
        boolean validateFileExtension, byte[] bytes)
        throws InternalServerErrorException;

    public void addFile(
        long workspaceId, long projectId, String fileName,
        boolean validateFileExtension, File file)
        throws InternalServerErrorException;

    public void addFile(
        long workspaceId, long projectId, String fileName,
        boolean validateFileExtension, InputStream is)
        throws InternalServerErrorException;

    public void addFile(
        long workspaceId, long projectId, String fileName, byte[] bytes)
        throws InternalServerErrorException;

    public void addFile(
        long workspaceId, long projectId, String fileName, File file)
        throws InternalServerErrorException;

    public void addFile(
        long workspaceId, long projectId, String fileName, InputStream is)
        throws InternalServerErrorException;

    public void checkRoot(long workspaceId);

    public void copyFileVersion(
        long workspaceId, long projectId, String fileName,
        String fromVersionLabel, String toVersionLabel)
        throws InternalServerErrorException;

    public void deleteDirectory(
        long workspaceId, long projectId, String dirName);

    public void deleteFile(long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException;

    public void deleteFile(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException;

    public File getFile(long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException;

    public File getFile(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException;

    public byte[] getFileAsBytes(
        long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException;

    public byte[] getFileAsBytes(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException;

    public InputStream getFileAsStream(
        long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException;

    public InputStream getFileAsStream(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException;

    public String[] getFileNames(
        long workspaceId, long projectId, String dirName)
        throws InternalServerErrorException;

    public long getFileSize(long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException;

    public boolean hasDirectory(
        long workspaceId, long projectId, String dirName)
        throws InternalServerErrorException;

    public boolean hasFile(long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException;

    public boolean hasFile(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException;

    public boolean isValidName(String name);

    public void move(String srcDir, String destDir);

    public void updateFile(
        long workspaceId, long projectId, long newprojectId,
        String fileName)
        throws InternalServerErrorException;

    public void updateFile(
        long workspaceId, long projectId, String fileName,
        String newFileName)
        throws InternalServerErrorException;

    public void updateFile(
        long workspaceId, long projectId, String fileName,
        String fileExtension, boolean validateFileExtension,
        String versionLabel, String sourceFileName, File file)
        throws InternalServerErrorException;

    public void updateFile(
        long workspaceId, long projectId, String fileName,
        String fileExtension, boolean validateFileExtension,
        String versionLabel, String sourceFileName, InputStream is)
        throws InternalServerErrorException;

    public void updateFileVersion(
        long workspaceId, long projectId, String fileName,
        String fromVersionLabel, String toVersionLabel)
        throws InternalServerErrorException;

    public void validate(String fileName, boolean validateFileExtension)
        throws InternalServerErrorException;

    public void validate(
        String fileName, boolean validateFileExtension, byte[] bytes)
        throws InternalServerErrorException;

    public void validate(
        String fileName, boolean validateFileExtension, File file)
        throws InternalServerErrorException;

    public void validate(
        String fileName, boolean validateFileExtension, InputStream is)
        throws InternalServerErrorException;

    public void validate(
        String fileName, String fileExtension, String sourceFileName,
        boolean validateFileExtension)
        throws InternalServerErrorException;

    public void validate(
        String fileName, String fileExtension, String sourceFileName,
        boolean validateFileExtension, File file)
        throws InternalServerErrorException;

    public void validate(
        String fileName, String fileExtension, String sourceFileName,
        boolean validateFileExtension, InputStream is)
        throws InternalServerErrorException;

    public void validateDirectoryName(String directoryName)
        throws InternalServerErrorException;
}
