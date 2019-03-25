package com.lagab.cmanager.service;

import com.lagab.cmanager.service.dto.UserDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing User.
 */
public interface UserService {

    /**
     * Save a user.
     *
     * @param userDTO the entity to save
     * @return the persisted entity
     */
    UserDTO save(UserDTO userDTO);

    /**
     * Get all the users.
     *
     * @return the list of entities
     */
    List<UserDTO> findAll();

    /**
     * Get all the User with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<UserDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" user.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserDTO> findOne(Long id);

    /**
     * Delete the "id" user.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
