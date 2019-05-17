package com.lagab.cmanager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Properties specific to Cm.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Store store = new Store();


    public Store getStore() {
        return store;
    }

    public static class Store {
        private String impl = "";
        private String rootDir = "";
        private String tmpDir = "";
        private String fileExtensions = "";
        private String charBlacklist = "";
        private String allowedMimeTypes = "";

        private int maxSize;

        private S3 s3;

        public Store(){}

        public String getImpl() {
            return impl;
        }

        public void setImpl(String impl) {
            this.impl = impl;
        }

        public String getRootDir() {
            return rootDir;
        }

        public void setRootDir(String rootDir) {
            this.rootDir = rootDir;
        }

        public String getTmpDir() {
            return tmpDir;
        }

        public void setTmpDir(String tmpDir) {
            this.tmpDir = tmpDir;
        }

        public String getFileExtensions() {
            return fileExtensions;
        }

        public void setFileExtensions(String fileExtensions) {
            this.fileExtensions = fileExtensions;
        }

        public String getCharBlacklist() {
            return charBlacklist;
        }

        public void setCharBlacklist(String charBlacklist) {
            this.charBlacklist = charBlacklist;
        }

        public int getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(int maxSize) {
            this.maxSize = maxSize;
        }

        public String getAllowedMimeTypes() {
            return allowedMimeTypes;
        }

        public Set<String> getAllowedMimeTypesList() {
            return new HashSet<>(Arrays.asList(allowedMimeTypes.trim().split(",")));
        }
        public Set<String> getFileExtensionsList() {
            return new HashSet<>(Arrays.asList(fileExtensions.trim().split(",")));
        }

        public void setAllowedMimeTypes(String allowedMimeTypes) {
            this.allowedMimeTypes = allowedMimeTypes;
        }

        public S3 getS3() {
            return s3;
        }

        public void setS3(S3 s3) {
            this.s3 = s3;
        }
    }

    public static class S3 {

        private String endpointUrl = "";
        private String accessKey = "";
        private String secretKey = "";
        private String bucketName = "";

        public String getEndpointUrl() {
            return endpointUrl;
        }

        public void setEndpointUrl(String endpointUrl) {
            this.endpointUrl = endpointUrl;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }
    }
}
