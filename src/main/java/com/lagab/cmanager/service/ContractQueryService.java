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

import com.lagab.cmanager.domain.Contract;
import com.lagab.cmanager.domain.*; // for static metamodels
import com.lagab.cmanager.repository.ContractRepository;
import com.lagab.cmanager.service.dto.ContractCriteria;
import com.lagab.cmanager.service.dto.ContractDTO;
import com.lagab.cmanager.service.mapper.ContractMapper;

/**
 * Service for executing complex queries for Contract entities in the database.
 * The main input is a {@link ContractCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ContractDTO} or a {@link Page} of {@link ContractDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContractQueryService extends QueryService<Contract> {

    private final Logger log = LoggerFactory.getLogger(ContractQueryService.class);

    private final ContractRepository contractRepository;

    private final ContractMapper contractMapper;

    public ContractQueryService(ContractRepository contractRepository, ContractMapper contractMapper) {
        this.contractRepository = contractRepository;
        this.contractMapper = contractMapper;
    }

    /**
     * Return a {@link List} of {@link ContractDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ContractDTO> findByCriteria(ContractCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Contract> specification = createSpecification(criteria);
        return contractMapper.toDto(contractRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ContractDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ContractDTO> findByCriteria(ContractCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Contract> specification = createSpecification(criteria);
        return contractRepository.findAll(specification, page)
            .map(contractMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContractCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Contract> specification = createSpecification(criteria);
        return contractRepository.count(specification);
    }

    /**
     * Function to convert ContractCriteria to a {@link Specification}
     */
    private Specification<Contract> createSpecification(ContractCriteria criteria) {
        Specification<Contract> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Contract_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Contract_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Contract_.description));
            }
            if (criteria.getSubject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubject(), Contract_.subject));
            }
            if (criteria.getExpiresAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiresAt(), Contract_.expiresAt));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Contract_.status));
            }
            if (criteria.getTemplate() != null) {
                specification = specification.and(buildSpecification(criteria.getTemplate(), Contract_.template));
            }
            if (criteria.getReminder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReminder(), Contract_.reminder));
            }
            if (criteria.getProjectId() != null) {
                specification = specification.and(buildSpecification(criteria.getProjectId(),
                    root -> root.join(Contract_.project, JoinType.LEFT).get(Project_.id)));
            }
        }
        return specification;
    }
}
