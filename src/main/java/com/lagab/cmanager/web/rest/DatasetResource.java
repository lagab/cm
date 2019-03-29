package com.lagab.cmanager.web.rest;
import com.lagab.cmanager.service.DatasetService;
import com.lagab.cmanager.web.rest.errors.BadRequestAlertException;
import com.lagab.cmanager.web.rest.util.HeaderUtil;
import com.lagab.cmanager.web.rest.util.PaginationUtil;
import com.lagab.cmanager.service.dto.DatasetDTO;
import com.lagab.cmanager.service.dto.DatasetCriteria;
import com.lagab.cmanager.service.DatasetQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Dataset.
 */
@RestController
@RequestMapping("/api")
public class DatasetResource {

    private final Logger log = LoggerFactory.getLogger(DatasetResource.class);

    private static final String ENTITY_NAME = "dataset";

    private final DatasetService datasetService;

    private final DatasetQueryService datasetQueryService;

    public DatasetResource(DatasetService datasetService, DatasetQueryService datasetQueryService) {
        this.datasetService = datasetService;
        this.datasetQueryService = datasetQueryService;
    }

    /**
     * POST  /datasets : Create a new dataset.
     *
     * @param datasetDTO the datasetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new datasetDTO, or with status 400 (Bad Request) if the dataset has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/datasets")
    public ResponseEntity<DatasetDTO> createDataset(@Valid @RequestBody DatasetDTO datasetDTO) throws URISyntaxException {
        log.debug("REST request to save Dataset : {}", datasetDTO);
        if (datasetDTO.getId() != null) {
            throw new BadRequestAlertException("A new dataset cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DatasetDTO result = datasetService.save(datasetDTO);
        return ResponseEntity.created(new URI("/api/datasets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /datasets : Updates an existing dataset.
     *
     * @param datasetDTO the datasetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated datasetDTO,
     * or with status 400 (Bad Request) if the datasetDTO is not valid,
     * or with status 500 (Internal Server Error) if the datasetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/datasets")
    public ResponseEntity<DatasetDTO> updateDataset(@Valid @RequestBody DatasetDTO datasetDTO) throws URISyntaxException {
        log.debug("REST request to update Dataset : {}", datasetDTO);
        if (datasetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DatasetDTO result = datasetService.save(datasetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, datasetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /datasets : get all the datasets.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of datasets in body
     */
    @GetMapping("/datasets")
    public ResponseEntity<List<DatasetDTO>> getAllDatasets(DatasetCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Datasets by criteria: {}", criteria);
        Page<DatasetDTO> page = datasetQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/datasets");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /datasets/count : count all the datasets.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/datasets/count")
    public ResponseEntity<Long> countDatasets(DatasetCriteria criteria) {
        log.debug("REST request to count Datasets by criteria: {}", criteria);
        return ResponseEntity.ok().body(datasetQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /datasets/:id : get the "id" dataset.
     *
     * @param id the id of the datasetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the datasetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/datasets/{id}")
    public ResponseEntity<DatasetDTO> getDataset(@PathVariable Long id) {
        log.debug("REST request to get Dataset : {}", id);
        Optional<DatasetDTO> datasetDTO = datasetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(datasetDTO);
    }

    /**
     * DELETE  /datasets/:id : delete the "id" dataset.
     *
     * @param id the id of the datasetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/datasets/{id}")
    public ResponseEntity<Void> deleteDataset(@PathVariable Long id) {
        log.debug("REST request to delete Dataset : {}", id);
        datasetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
