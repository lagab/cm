package com.lagab.cmanager.web.rest;
import com.lagab.cmanager.domain.Authority;
import com.lagab.cmanager.repository.AuthorityRepository;
import com.lagab.cmanager.web.rest.errors.BadRequestAlertException;
import com.lagab.cmanager.web.rest.util.HeaderUtil;
import com.lagab.cmanager.service.dto.AuthorityDTO;
import com.lagab.cmanager.service.mapper.AuthorityMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Authority.
 */
@RestController
@RequestMapping("/api")
public class AuthorityResource {

    private final Logger log = LoggerFactory.getLogger(AuthorityResource.class);

    private static final String ENTITY_NAME = "cmAuthority";

    private final AuthorityRepository authorityRepository;

    private final AuthorityMapper authorityMapper;

    public AuthorityResource(AuthorityRepository authorityRepository, AuthorityMapper authorityMapper) {
        this.authorityRepository = authorityRepository;
        this.authorityMapper = authorityMapper;
    }

    /**
     * POST  /authorities : Create a new authority.
     *
     * @param authorityDTO the authorityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new authorityDTO, or with status 400 (Bad Request) if the authority has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/authorities")
    public ResponseEntity<AuthorityDTO> createAuthority(@Valid @RequestBody AuthorityDTO authorityDTO) throws URISyntaxException {
        log.debug("REST request to save Authority : {}", authorityDTO);
        if (authorityDTO.getId() != null) {
            throw new BadRequestAlertException("A new authority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Authority authority = authorityMapper.toEntity(authorityDTO);
        authority = authorityRepository.save(authority);
        AuthorityDTO result = authorityMapper.toDto(authority);
        return ResponseEntity.created(new URI("/api/authorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /authorities : Updates an existing authority.
     *
     * @param authorityDTO the authorityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated authorityDTO,
     * or with status 400 (Bad Request) if the authorityDTO is not valid,
     * or with status 500 (Internal Server Error) if the authorityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/authorities")
    public ResponseEntity<AuthorityDTO> updateAuthority(@Valid @RequestBody AuthorityDTO authorityDTO) throws URISyntaxException {
        log.debug("REST request to update Authority : {}", authorityDTO);
        if (authorityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Authority authority = authorityMapper.toEntity(authorityDTO);
        authority = authorityRepository.save(authority);
        AuthorityDTO result = authorityMapper.toDto(authority);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, authorityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /authorities : get all the authorities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of authorities in body
     */
    @GetMapping("/authorities")
    public List<AuthorityDTO> getAllAuthorities() {
        log.debug("REST request to get all Authorities");
        List<Authority> authorities = authorityRepository.findAll();
        return authorityMapper.toDto(authorities);
    }

    /**
     * GET  /authorities/:id : get the "id" authority.
     *
     * @param id the id of the authorityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the authorityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/authorities/{id}")
    public ResponseEntity<AuthorityDTO> getAuthority(@PathVariable Long id) {
        log.debug("REST request to get Authority : {}", id);
        Optional<AuthorityDTO> authorityDTO = authorityRepository.findById(id)
            .map(authorityMapper::toDto);
        return ResponseUtil.wrapOrNotFound(authorityDTO);
    }

    /**
     * DELETE  /authorities/:id : delete the "id" authority.
     *
     * @param id the id of the authorityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/authorities/{id}")
    public ResponseEntity<Void> deleteAuthority(@PathVariable Long id) {
        log.debug("REST request to delete Authority : {}", id);
        authorityRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
