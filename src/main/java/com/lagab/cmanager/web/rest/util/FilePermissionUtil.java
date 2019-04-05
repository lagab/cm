package com.lagab.cmanager.web.rest.util;

/**
 * @author GABRIEL
 * @since 05/04/2019.
 */
public class FilePermissionUtil {

    public static void checkCopy(String source, String destination) {
        _pacl.checkCopy(source, destination);
    }

    public static void checkDelete(String path) {
        _pacl.checkDelete(path);
    }

    public static void checkMove(String source, String destination) {
        _pacl.checkMove(source, destination);
    }

    public static void checkRead(String path) {
        _pacl.checkRead(path);
    }

    public static void checkWrite(String path) {
        _pacl.checkWrite(path);
    }

    public interface PACL {

        public void checkCopy(String source, String destination);

        public void checkDelete(String path);

        public void checkMove(String source, String destination);

        public void checkRead(String path);

        public void checkWrite(String path);

    }

    private static final PACL _pacl = new NoPACL();

    private static class NoPACL implements PACL {

        @Override
        public void checkCopy(String source, String destination) {
        }

        @Override
        public void checkDelete(String path) {
        }

        @Override
        public void checkMove(String source, String destination) {
        }

        @Override
        public void checkRead(String path) {
        }

        @Override
        public void checkWrite(String path) {
        }

    }
}
