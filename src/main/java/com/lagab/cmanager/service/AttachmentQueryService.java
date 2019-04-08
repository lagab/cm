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

import com.lagab.cmanager.domain.Attachment;
import com.lagab.cmanager.domain.*; // for static metamodels
import com.lagab.cmanager.repository.AttachmentRepository;
import com.lagab.cmanager.service.dto.AttachmentCriteria;
import com.lagab.cmanager.service.dto.AttachmentDTO;
import com.lagab.cmanager.service.mapper.AttachmentMapper;

/**
 * Service for executing complex queries for Attachment entities in the database.
 * The main input is a {@link AttachmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AttachmentDTO} or a {@link Page} of {@link AttachmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AttachmentQueryService extends QueryService<Attachment> {

    private final Logger log = LoggerFactory.getLogger(AttachmentQueryService.class);

    private final AttachmentRepository attachmentRepository;

    private final AttachmentMapper attachmentMapper;

    public AttachmentQueryService(AttachmentRepository attachmentRepository, AttachmentMapper attachmentMapper) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentMapper = attachmentMapper;
    }

    /**
     * Return a {@link List} of {@link AttachmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AttachmentDTO> findByCriteria(AttachmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Attachment> specification = createSpecification(criteria);
        return attachmentMapper.toDto(attachmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AttachmentDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AttachmentDTO> findByCriteria(AttachmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Attachment> specification = createSpecification(criteria);
        return attachmentRepository.findAll(specification, page)
            .map(attachmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AttachmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Attachment> specification = createSpecification(criteria);
        return attachmentRepository.count(specification);
    }

    /**
     * Function to convert AttachmentCriteria to a {@link Specification}
     */
    private Specification<Attachment> createSpecification(AttachmentCriteria criteria) {
        Specification<Attachment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Attachment_.id));
            }
            if (criteria.getEntityId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEntityId(), Attachment_.entityId));
            }
            if (criteria.getEntityType() != null) {
                specification = specification.and(buildSpecification(criteria.getEntityType(), Attachment_.entityType));
            }
            if (criteria.getFilename() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFilename(), Attachment_.filename));
            }
            if (criteria.getDiskFilename() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiskFilename(), Attachment_.diskFilename));
            }
            if (criteria.getFileSize() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFileSize(), Attachment_.fileSize));
            }
            if (criteria.getContentType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContentType(), Attachment_.contentType));
            }
            if (criteria.getDownloads() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDownloads(), Attachment_.downloads));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Attachment_.description));
            }
        }
        return specification;
    }
}
