package com.lagab.cmanager.web.rest.util;

import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import java.util.List;
import java.util.Properties;

/**
 * @author GABRIEL
 * @since 05/04/2019.
 */
public class FileUtil {

    public static final String TMP_DIR = "/tmp";

    public static String appendParentheticalSuffix(String fileName, String suffix) {
        return getFile().appendParentheticalSuffix(fileName, suffix);
    }

    public static void copyDirectory(File source, File destination)
        throws IOException {

        FilePermissionUtil.checkCopy(_getPath(source), _getPath(destination));
        getFile().copyDirectory(source, destination);
    }

    public static void copyDirectory(
        String sourceDirName, String destinationDirName)
        throws IOException {

        FilePermissionUtil.checkCopy(sourceDirName, destinationDirName);
        getFile().copyDirectory(sourceDirName, destinationDirName);
    }

    public static void copyFile(File source, File destination)
        throws IOException {

        FilePermissionUtil.checkCopy(_getPath(source), _getPath(destination));
        getFile().copyFile(source, destination);
    }

    public static void copyFile(File source, File destination, boolean lazy)
        throws IOException {

        FilePermissionUtil.checkCopy(_getPath(source), _getPath(destination));
        getFile().copyFile(source, destination, lazy);
    }

    public static void copyFile(String source, String destination)
        throws IOException {

        FilePermissionUtil.checkCopy(source, destination);
        getFile().copyFile(source, destination);
    }

    public static void copyFile(String source, String destination, boolean lazy)
        throws IOException {

        FilePermissionUtil.checkCopy(source, destination);
        getFile().copyFile(source, destination, lazy);
    }

    public static File createTempFile() {
        FilePermissionUtil.checkWrite(TMP_DIR);
        return getFile().createTempFile();
    }

    public static File createTempFile(byte[] bytes) throws IOException {
        FilePermissionUtil.checkWrite(TMP_DIR);
        return getFile().createTempFile(bytes);
    }

    public static File createTempFile(InputStream is) throws IOException {
        FilePermissionUtil.checkWrite(TMP_DIR);
        return getFile().createTempFile(is);
    }

    public static File createTempFile(String extension) {
        FilePermissionUtil.checkWrite( TMP_DIR);
        return getFile().createTempFile(extension);
    }

    public static File createTempFile(String prefix, String extension) {
        FilePermissionUtil.checkWrite(TMP_DIR);
        return getFile().createTempFile(prefix, extension);
    }

    public static String createTempFileName() {
        FilePermissionUtil.checkWrite(TMP_DIR);
        return getFile().createTempFileName();
    }

    public static String createTempFileName(String extension) {
        FilePermissionUtil.checkWrite(TMP_DIR);
        return getFile().createTempFileName(extension);
    }

    public static String createTempFileName(String prefix, String extension) {
        FilePermissionUtil.checkWrite(TMP_DIR);
        return getFile().createTempFileName(prefix, extension);
    }

    public static File createTempFolder() throws IOException {
        FilePermissionUtil.checkWrite(TMP_DIR);
        return getFile().createTempFolder();
    }

    public static String decodeSafeFileName(String fileName) {
        return getFile().decodeSafeFileName(fileName);
    }

    public static boolean delete(File file) {
        FilePermissionUtil.checkDelete(_getPath(file));
        return getFile().delete(file);
    }

    public static boolean delete(String file) {
        FilePermissionUtil.checkDelete(file);
        return getFile().delete(file);
    }

    public static void deltree(File directory) {
        FilePermissionUtil.checkDelete(_getPath(directory));
        getFile().deltree(directory);
    }

    public static void deltree(String directory) {
        FilePermissionUtil.checkDelete(directory);
        getFile().deltree(directory);
    }

    public static String encodeSafeFileName(String fileName) {
        return getFile().encodeSafeFileName(fileName);
    }

    public static boolean exists(File file) {
        FilePermissionUtil.checkRead(_getPath(file));
        return getFile().exists(file);
    }

    public static boolean exists(String fileName) {
        FilePermissionUtil.checkRead(fileName);
        return getFile().exists(fileName);
    }

    /**
     * Extract text from an input stream and file name.
     *
     * @param  is input stream of file
     * @param  fileName full name or extension of file (e.g., "Test.doc",
     *         ".doc")
     * @return Extracted text if it is a supported format or an empty string if
     *         it is an unsupported format
     */
    public static String extractText(InputStream is, String fileName) {
        return getFile().extractText(is, fileName);
    }

    public static String extractText(
        InputStream is, String fileName, int maxStringLength) {
        return getFile().extractText(is, fileName, maxStringLength);
    }

    public static String[] find(
        String directory, String includes, String excludes) {
        FilePermissionUtil.checkRead(directory);
        return getFile().find(directory, includes, excludes);
    }

    public static String getAbsolutePath(File file) {
        return getFile().getAbsolutePath(file);
    }

    public static byte[] getBytes(File file) throws IOException {
        FilePermissionUtil.checkRead(_getPath(file));
        return getFile().getBytes(file);
    }

    public static byte[] getBytes(InputStream is) throws IOException {
        return getFile().getBytes(is);
    }

    public static byte[] getBytes(InputStream is, int bufferSize)
        throws IOException {

        return getFile().getBytes(is);
    }

    public static byte[] getBytes(
        InputStream is, int bufferSize, boolean cleanUpStream)
        throws IOException {

        return getFile().getBytes(is, bufferSize, cleanUpStream);
    }

    public static String getExtension(String fileName) {
        return getFile().getExtension(fileName);
    }

    public static String getMD5Checksum(java.io.File file) throws IOException {
        return getFile().getMD5Checksum(file);
    }

    public static String getPath(String fullFileName) {
        return getFile().getPath(fullFileName);
    }

    public static String getShortFileName(String fullFileName) {
        return getFile().getShortFileName(fullFileName);
    }

    public static boolean isAscii(File file) throws IOException {
        FilePermissionUtil.checkRead(_getPath(file));
        return getFile().isAscii(file);
    }

    public static boolean isSameContent(File file, byte[] bytes, int length) {
        FilePermissionUtil.checkRead(_getPath(file));
        return getFile().isSameContent(file, bytes, length);
    }

    public static boolean isSameContent(File file, String s) {
        FilePermissionUtil.checkRead(_getPath(file));
        return getFile().isSameContent(file, s);
    }

    public static String[] listDirs(File file) {
        FilePermissionUtil.checkRead(_getPath(file));
        return getFile().listDirs(file);
    }

    public static String[] listDirs(String fileName) {
        FilePermissionUtil.checkRead(fileName);
        return getFile().listDirs(fileName);
    }

    public static String[] listFiles(File file) {
        FilePermissionUtil.checkRead(_getPath(file));
        return getFile().listFiles(file);
    }

    public static String[] listFiles(String fileName) {
        FilePermissionUtil.checkRead(fileName);
        return getFile().listFiles(fileName);
    }

    public static void mkdirs(File file) throws IOException {
        FilePermissionUtil.checkCopy(_getPath(file), _getPath(file));
        getFile().mkdirs(file);
    }

    public static void mkdirs(String pathName) {
        FilePermissionUtil.checkCopy(pathName, pathName);
        getFile().mkdirs(pathName);
    }

    public static boolean move(File source, File destination) {
        FilePermissionUtil.checkMove(_getPath(source), _getPath(destination));
        return getFile().move(source, destination);
    }

    public static boolean move(
        String sourceFileName, String destinationFileName) {
        FilePermissionUtil.checkMove(sourceFileName, destinationFileName);
        return getFile().move(sourceFileName, destinationFileName);
    }

    public static String read(File file) throws IOException {
        FilePermissionUtil.checkRead(_getPath(file));
        return getFile().read(file);
    }

    public static String read(File file, boolean raw) throws IOException {
        FilePermissionUtil.checkRead(_getPath(file));
        return getFile().read(file, raw);
    }

    public static String read(String fileName) throws IOException {
        FilePermissionUtil.checkRead(fileName);
        return getFile().read(fileName);
    }

    public static String replaceSeparator(String fileName) {
        return getFile().replaceSeparator(fileName);
    }

    public static File[] sortFiles(File[] files) {
        return getFile().sortFiles(files);
    }

    public static String stripExtension(String fileName) {
        return getFile().stripExtension(fileName);
    }

    public static List<String> toList(Reader reader) {
        return getFile().toList(reader);
    }

    public static List<String> toList(String fileName) {
        return getFile().toList(fileName);
    }

    public static Properties toProperties(FileInputStream fis) {
        return getFile().toProperties(fis);
    }

    public static Properties toProperties(String fileName) {
        FilePermissionUtil.checkRead(fileName);
        return getFile().toProperties(fileName);
    }

    public static void touch(File file) throws IOException {
        FilePermissionUtil.checkWrite(_getPath(file));
        getFile().touch(file);
    }

    public static void touch(String fileName) throws IOException {
        FilePermissionUtil.checkWrite(fileName);
        getFile().touch(fileName);
    }

    public static void unzip(File source, File destination) {
        FilePermissionUtil.checkCopy(_getPath(source), _getPath(destination));
        getFile().unzip(source, destination);
    }

    public static void write(File file, byte[] bytes) throws IOException {
        write(file, bytes, false);
    }

    public static void write(File file, byte[] bytes, boolean append)
        throws IOException {

        FilePermissionUtil.checkWrite(_getPath(file));
        getFile().write(file, bytes, append);
    }

    public static void write(File file, byte[] bytes, int offset, int length)
        throws IOException {

        write(file, bytes, offset, length, false);
    }

    public static void write(
        File file, byte[] bytes, int offset, int length, boolean append)
        throws IOException {

        FilePermissionUtil.checkWrite(_getPath(file));
        getFile().write(file, bytes, offset, length, append);
    }

    public static void write(File file, InputStream is) throws IOException {
        FilePermissionUtil.checkWrite(_getPath(file));
        getFile().write(file, is);
    }

    public static void write(File file, String s) throws IOException {
        FilePermissionUtil.checkWrite(_getPath(file));
        getFile().write(file, s);
    }

    public static void write(File file, String s, boolean lazy)
        throws IOException {

        FilePermissionUtil.checkWrite(_getPath(file));
        getFile().write(file, s, lazy);
    }

    public static void write(File file, String s, boolean lazy, boolean append)
        throws IOException {

        FilePermissionUtil.checkWrite(_getPath(file));
        getFile().write(file, s, lazy, append);
    }

    public static void write(String fileName, byte[] bytes) throws IOException {
        FilePermissionUtil.checkWrite(fileName);
        getFile().write(fileName, bytes);
    }

    public static void write(String fileName, InputStream is)
        throws IOException {

        FilePermissionUtil.checkWrite(fileName);
        getFile().write(fileName, is);
    }

    public static void write(String fileName, String s) throws IOException {
        FilePermissionUtil.checkWrite(fileName);
        getFile().write(fileName, s);
    }

    public static void write(String fileName, String s, boolean lazy)
        throws IOException {

        FilePermissionUtil.checkWrite(fileName);
        getFile().write(fileName, s, lazy);
    }

    public static void write(
        String fileName, String s, boolean lazy, boolean append)
        throws IOException {

        FilePermissionUtil.checkWrite(fileName);
        getFile().write(fileName, s, lazy, append);
    }

    public static void write(String pathName, String fileName, String s)
        throws IOException {

        FilePermissionUtil.checkWrite(pathName);
        getFile().write(pathName, fileName, s);
    }

    public static void write(
        String pathName, String fileName, String s, boolean lazy)
        throws IOException {

        FilePermissionUtil.checkWrite(pathName);
        getFile().write(pathName, fileName, s, lazy);
    }

    public static void write(
        String pathName, String fileName, String s, boolean lazy,
        boolean append)
        throws IOException {

        FilePermissionUtil.checkWrite(pathName);
        getFile().write(pathName, fileName, s, lazy, append);
    }

    public static com.lagab.cmanager.web.rest.util.File getFile() {
        return _file;
    }

    public void setFile(com.lagab.cmanager.web.rest.util.File file) {
        _file = file;
    }

    private static String _getPath(File file) {
        if (file == null) {
            return null;
        }
        return file.getPath();
    }

    private static com.lagab.cmanager.web.rest.util.File _file;

    @Autowired
    public FileUtil(com.lagab.cmanager.web.rest.util.File file) {
        _file = file;
    }
}
