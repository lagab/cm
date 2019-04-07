package com.lagab.cmanager.service;

import com.lagab.cmanager.web.rest.errors.*;

import java.io.File;
import java.io.InputStream;

/**
 * @author GABRIEL
 * @since 05/04/2019.
 */
public interface Store {

    public static final String VERSION_DEFAULT = "1.0";

    public void addDirectory(long workspaceId, long projectId, String dirName) throws SystemException;

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
        throws SystemException;

    public void addFile(
        long workspaceId, long projectId, String fileName, File file)
        throws SystemException;

    public void addFile(
        long workspaceId, long projectId, String fileName, InputStream is)
        throws InternalServerErrorException, SystemException;

    public void checkRoot(long workspaceId);

    public void copyFileVersion(
        long workspaceId, long projectId, String fileName,
        String fromVersionLabel, String toVersionLabel)
        throws InternalServerErrorException, SystemException;

    public void deleteDirectory(
        long workspaceId, long projectId, String dirName) throws SystemException;

    public void deleteFile(long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException, SystemException;

    public void deleteFile(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException, SystemException;

    public File getFile(long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException, SystemException;

    public File getFile(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException, SystemException;

    public byte[] getFileAsBytes(
        long workspaceId, long projectId, String fileName)
        throws SystemException;

    public byte[] getFileAsBytes(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException, SystemException;

    public InputStream getFileAsStream(
        long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException, SystemException;

    public InputStream getFileAsStream(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException, SystemException;

    public String[] getFileNames(
        long workspaceId, long projectId, String dirName)
        throws InternalServerErrorException, SystemException;

    public long getFileSize(long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException, SystemException;

    public boolean hasDirectory(
        long workspaceId, long projectId, String dirName)
        throws InternalServerErrorException, SystemException;

    public boolean hasFile(long workspaceId, long projectId, String fileName)
        throws InternalServerErrorException;

    public boolean hasFile(
        long workspaceId, long projectId, String fileName,
        String versionLabel)
        throws InternalServerErrorException;


    public void move(String srcDir, String destDir);

    public void updateFile(
        long workspaceId, long projectId, long newprojectId,
        String fileName)
        throws InternalServerErrorException, SystemException;

    public void updateFile(
        long companyId, long repositoryId, String fileName,
        String versionLabel, byte[] bytes)
        throws SystemException;

    public void updateFile(
        long companyId, long repositoryId, String fileName,
        String versionLabel, File file)
        throws SystemException;

    public void updateFile(
        long companyId, long repositoryId, String fileName,
        String versionLabel, InputStream is)
        throws SystemException;

    public void updateFile(
        long workspaceId, long projectId, String fileName,
        String newFileName)
        throws InternalServerErrorException, SystemException;

    /*public void updateFile(
        long workspaceId, long projectId, String fileName,
        String fileExtension, boolean validateFileExtension,
        String versionLabel, String sourceFileName, File file)
        throws InternalServerErrorException;

    public void updateFile(
        long workspaceId, long projectId, String fileName,
        String fileExtension, boolean validateFileExtension,
        String versionLabel, String sourceFileName, InputStream is)
        throws InternalServerErrorException;*/

    public void updateFileVersion(
        long workspaceId, long projectId, String fileName,
        String fromVersionLabel, String toVersionLabel)
        throws InternalServerErrorException, SystemException;

    public void validate(String fileName, boolean validateFileExtension)
        throws InternalServerErrorException, FileNameException, FileExtensionException;

    public void validate(
        String fileName, boolean validateFileExtension, byte[] bytes)
        throws InternalServerErrorException, FileSizeException, FileNameException, FileExtensionException;

    public void validate(
        String fileName, boolean validateFileExtension, File file)
        throws InternalServerErrorException, FileSizeException, FileNameException, FileExtensionException;

    public void validate(
        String fileName, boolean validateFileExtension, InputStream is)
        throws InternalServerErrorException, FileSizeException, FileNameException, FileExtensionException;

}
