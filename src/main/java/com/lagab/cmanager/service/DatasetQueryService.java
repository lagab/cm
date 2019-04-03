package com.lagab.cmanager.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.lagab.cmanager.domain.Dataset;
import com.lagab.cmanager.domain.*; // for static metamodels
import com.lagab.cmanager.repository.DatasetRepository;
import com.lagab.cmanager.service.dto.DatasetCriteria;
import com.lagab.cmanager.service.dto.DatasetDTO;
import com.lagab.cmanager.service.mapper.DatasetMapper;

/**
 * Service for executing complex queries for Dataset entities in the database.
 * The main input is a {@link DatasetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DatasetDTO} or a {@link Page} of {@link DatasetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DatasetQueryService extends QueryService<Dataset> {

    private final Logger log = LoggerFactory.getLogger(DatasetQueryService.class);

    private final DatasetRepository datasetRepository;

    private final DatasetMapper datasetMapper;

    public DatasetQueryService(DatasetRepository datasetRepository, DatasetMapper datasetMapper) {
        this.datasetRepository = datasetRepository;
        this.datasetMapper = datasetMapper;
    }

    /**
     * Return a {@link List} of {@link DatasetDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DatasetDTO> findByCriteria(DatasetCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Dataset> specification = createSpecification(criteria);
        return datasetMapper.toDto(datasetRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DatasetDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DatasetDTO> findByCriteria(DatasetCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Dataset> specification = createSpecification(criteria);
        return datasetRepository.findAll(specification, page)
            .map(datasetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DatasetCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Dataset> specification = createSpecification(criteria);
        return datasetRepository.count(specification);
    }

    /**
     * Function to convert DatasetCriteria to a {@link Specification}
     */
    private Specification<Dataset> createSpecification(DatasetCriteria criteria) {
        Specification<Dataset> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Dataset_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Dataset_.name));
            }
            if (criteria.getKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getKey(), Dataset_.key));
            }
            if (criteria.getHeaders() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHeaders(), Dataset_.headers));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectId(),
                    root -> root.join(Dataset_.project, JoinType.LEFT).get(Project_.id)));
            }
        }
        return specification;
    }
}
