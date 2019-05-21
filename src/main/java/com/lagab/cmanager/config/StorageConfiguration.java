package com.lagab.cmanager.config;

import com.lagab.cmanager.store.Store;
import com.lagab.cmanager.store.impl.FileSystemStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Constructor;

/**
 * @author gabriel
 * @since 21/05/2019.
 */
@Configuration
public class StorageConfiguration {

    private final Logger log = LoggerFactory.getLogger(StorageConfiguration.class);

    private final StorageProperties storageProperties;

    public StorageConfiguration(StorageProperties storageProperties){
        this.storageProperties = storageProperties;
    }

    @Bean(name = "store")
    public Store getStore(){
        Store store = null;
        if (ClassUtils.isPresent(storageProperties.getImpl(),getClass().getClassLoader())){
            try {
                Class<?> storeClass = Class.forName(storageProperties.getImpl(),true,getClass().getClassLoader());
                Constructor<?> constructor = storeClass.getConstructor(StorageProperties.class);
                store = (Store) constructor.newInstance(storageProperties);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        if(store == null){
            log.debug(storageProperties.getImpl() +"not found, return default FileStorage" );
            store = new FileSystemStore(storageProperties);
        }
        return store;
    }
}
