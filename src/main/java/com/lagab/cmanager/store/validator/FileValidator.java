package com.lagab.cmanager.store.validator;

import com.lagab.cmanager.config.StorageProperties;
import com.lagab.cmanager.store.errors.DuplicateFileException;
import com.lagab.cmanager.store.errors.FileNameException;
import com.lagab.cmanager.store.errors.InvalidContentTypeException;
import com.lagab.cmanager.store.errors.InvalidExtensionException;
import com.lagab.cmanager.web.rest.errors.SystemException;
import com.lagab.cmanager.web.rest.util.StringConstants;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * TODO : to continue
 */
@Service
public class FileValidator {

    private final Logger log = LoggerFactory.getLogger(FileValidator.class);


    private StorageProperties storageProperties;

    public FileValidator(StorageProperties storageProperties){
        this.storageProperties = storageProperties;
        this.allowedExtensions = storageProperties.getFileExtensionsList();
        this.allowedMimeTypes = storageProperties.getAllowedMimeTypesList();
    }

    public static final String FILE_NAME_REGEX = "^[a-zA-Z0-9!@#$%^&{}\\[\\]()_+\\-=,.~'` ]{1,255}$";

    /**
     * The file extension that will be allowed by this validator
     */
    Set<String> allowedExtensions = new HashSet<>();
    Set<String> allowedMimeTypes = new HashSet<>();

    public void validateFile(MultipartFile file) throws SystemException {
        if ( !isvalidFileName(file.getOriginalFilename()) ){
            throw  new FileNameException("FileName Invalid :"+ file.getOriginalFilename());
        }
        if ( !isSupportedExtension(file.getOriginalFilename()) ){
            throw  new InvalidExtensionException("extension "+ FilenameUtils.getExtension(file.getOriginalFilename() +" are invalid "));
        }
         if ( !isSupportedContentType(file.getContentType()) ){
             throw  new InvalidContentTypeException("Content Type "+ file.getContentType() +" are invalid ");
         }
    }

    public void validateFile(String path, MultipartFile file) throws SystemException{
        validateFile(file);
        if( Files.exists(Paths.get(path + StringConstants.SLASH + file.getOriginalFilename())) ){
            throw new DuplicateFileException("File already exists :" + file.getOriginalFilename());
        }
    }


    public  boolean  isSupportedContentType(String contentType){
        return allowedMimeTypes.contains(contentType);
    }

    public  boolean isvalidFileName(String fileName){
        return fileName.matches(FILE_NAME_REGEX);
    }
    public  boolean isSupportedExtension(String fileName){
        String extension = FilenameUtils.getExtension(fileName);
        if( storageProperties.getFileExtensions().equals("*")){
            return true;
        }else{
            if( !extension.equals("")){
                extension = "."+extension;
            }
            return allowedExtensions.contains(extension);

        }
    }

}
