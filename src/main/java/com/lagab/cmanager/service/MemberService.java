package com.lagab.cmanager.service;

import com.lagab.cmanager.service.dto.MemberDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Member.
 */
public interface MemberService {

    /**
     * Save a member.
     *
     * @param memberDTO the entity to save
     * @return the persisted entity
     */
    MemberDTO save(MemberDTO memberDTO);

    /**
     * Get all the members.
     *
     * @return the list of entities
     */
    List<MemberDTO> findAll();


    /**
     * Get the "id" member.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MemberDTO> findOne(Long id);

    /**
     * Delete the "id" member.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
