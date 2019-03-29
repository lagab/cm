package com.lagab.cmanager.service.impl;

import com.lagab.cmanager.service.DatasetService;
import com.lagab.cmanager.domain.Dataset;
import com.lagab.cmanager.repository.DatasetRepository;
import com.lagab.cmanager.service.dto.DatasetDTO;
import com.lagab.cmanager.service.mapper.DatasetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Dataset.
 */
@Service
@Transactional
public class DatasetServiceImpl implements DatasetService {

    private final Logger log = LoggerFactory.getLogger(DatasetServiceImpl.class);

    private final DatasetRepository datasetRepository;

    private final DatasetMapper datasetMapper;

    public DatasetServiceImpl(DatasetRepository datasetRepository, DatasetMapper datasetMapper) {
        this.datasetRepository = datasetRepository;
        this.datasetMapper = datasetMapper;
    }

    /**
     * Save a dataset.
     *
     * @param datasetDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DatasetDTO save(DatasetDTO datasetDTO) {
        log.debug("Request to save Dataset : {}", datasetDTO);
        Dataset dataset = datasetMapper.toEntity(datasetDTO);
        dataset = datasetRepository.save(dataset);
        return datasetMapper.toDto(dataset);
    }

    /**
     * Get all the datasets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DatasetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Datasets");
        return datasetRepository.findAll(pageable)
            .map(datasetMapper::toDto);
    }


    /**
     * Get one dataset by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DatasetDTO> findOne(Long id) {
        log.debug("Request to get Dataset : {}", id);
        return datasetRepository.findById(id)
            .map(datasetMapper::toDto);
    }

    /**
     * Delete the dataset by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dataset : {}", id);
        datasetRepository.deleteById(id);
    }
}
