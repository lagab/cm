package com.lagab.cmanager.service;

import com.lagab.cmanager.service.dto.DatasetDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Dataset.
 */
public interface DatasetService {

    /**
     * Save a dataset.
     *
     * @param datasetDTO the entity to save
     * @return the persisted entity
     */
    DatasetDTO save(DatasetDTO datasetDTO);

    /**
     * Get all the datasets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DatasetDTO> findAll(Pageable pageable);


    /**
     * Get the "id" dataset.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DatasetDTO> findOne(Long id);

    /**
     * Delete the "id" dataset.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
