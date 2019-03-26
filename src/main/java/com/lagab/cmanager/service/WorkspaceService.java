package com.lagab.cmanager.service;

import com.lagab.cmanager.service.dto.WorkspaceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Workspace.
 */
public interface WorkspaceService {

    /**
     * Save a workspace.
     *
     * @param workspaceDTO the entity to save
     * @return the persisted entity
     */
    WorkspaceDTO save(WorkspaceDTO workspaceDTO);

    /**
     * Get all the workspaces.
     *
     * @return the list of entities
     */
    List<WorkspaceDTO> findAll();


    /**
     * Get the "id" workspace.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<WorkspaceDTO> findOne(Long id);

    /**
     * Delete the "id" workspace.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
