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

import com.lagab.cmanager.domain.Project;
import com.lagab.cmanager.domain.*; // for static metamodels
import com.lagab.cmanager.repository.ProjectRepository;
import com.lagab.cmanager.service.dto.ProjectCriteria;
import com.lagab.cmanager.service.dto.ProjectDTO;
import com.lagab.cmanager.service.mapper.ProjectMapper;

/**
 * Service for executing complex queries for Project entities in the database.
 * The main input is a {@link ProjectCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProjectDTO} or a {@link Page} of {@link ProjectDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProjectQueryService extends QueryService<Project> {

    private final Logger log = LoggerFactory.getLogger(ProjectQueryService.class);

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    public ProjectQueryService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    /**
     * Return a {@link List} of {@link ProjectDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProjectDTO> findByCriteria(ProjectCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Project> specification = createSpecification(criteria);
        return projectMapper.toDto(projectRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProjectDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProjectDTO> findByCriteria(ProjectCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Project> specification = createSpecification(criteria);
        return projectRepository.findAll(specification, page)
            .map(projectMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProjectCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Project> specification = createSpecification(criteria);
        return projectRepository.count(specification);
    }

    /**
     * Function to convert ProjectCriteria to a {@link Specification}
     */
    private Specification<Project> createSpecification(ProjectCriteria criteria) {
        Specification<Project> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Project_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Project_.name));
            }
            if (criteria.getArchived() != null) {
                specification = specification.and(buildSpecification(criteria.getArchived(), Project_.archived));
            }
            if (criteria.getVisibility() != null) {
                specification = specification.and(buildSpecification(criteria.getVisibility(), Project_.visibility));
            }
            if (criteria.getPath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPath(), Project_.path));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Project_.description));
            }
            if (criteria.getImageUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageUrl(), Project_.imageUrl));
            }
            if (criteria.getWorkspaceId() != null) {
                specification = specification.and(buildSpecification(criteria.getWorkspaceId(),
                    root -> root.join(Project_.workspace, JoinType.LEFT).get(Workspace_.id)));
            }
            if (criteria.getWorkspacePath() != null) {
                specification = specification.and(buildSpecification(criteria.getWorkspacePath(),
                    root -> root.join(Project_.workspace, JoinType.LEFT).get(Workspace_.path)));
            }
        }
        return specification;
    }
}
