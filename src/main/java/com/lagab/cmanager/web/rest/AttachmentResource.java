package com.lagab.cmanager.web.rest;
import com.lagab.cmanager.domain.Attachment;
import com.lagab.cmanager.domain.enumeration.EntityType;
import com.lagab.cmanager.service.AttachmentService;
import com.lagab.cmanager.service.dto.AttachmentFileDTO;
import com.lagab.cmanager.store.Store;
import com.lagab.cmanager.store.validator.FileValidator;
import com.lagab.cmanager.web.rest.errors.AttachmentNotFoundException;
import com.lagab.cmanager.web.rest.errors.BadRequestAlertException;
import com.lagab.cmanager.web.rest.errors.SystemException;
import com.lagab.cmanager.web.rest.util.HeaderUtil;
import com.lagab.cmanager.web.rest.util.PaginationUtil;
import com.lagab.cmanager.service.dto.AttachmentDTO;
import com.lagab.cmanager.service.dto.AttachmentCriteria;
import com.lagab.cmanager.service.AttachmentQueryService;
import com.lagab.cmanager.web.rest.util.StringConstants;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Attachment.
 */
@RestController
@RequestMapping("/api")
public class AttachmentResource {

    private final Logger log = LoggerFactory.getLogger(AttachmentResource.class);

    private static final String ENTITY_NAME = "attachment";

    private final AttachmentService attachmentService;

    private final AttachmentQueryService attachmentQueryService;

    private final FileValidator fileValidator;

    private  final Store store;

    public AttachmentResource(AttachmentService attachmentService, AttachmentQueryService attachmentQueryService,FileValidator fileValidator, Store store) {
        this.attachmentService = attachmentService;
        this.attachmentQueryService = attachmentQueryService;
        this.fileValidator = fileValidator;
        this.store = store;
    }

    /**
     * POST  /attachments : Create a new attachment.
     *
     * @param attachmentDTO the attachmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attachmentDTO, or with status 400 (Bad Request) if the attachment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/attachments")
    public ResponseEntity<AttachmentDTO> createAttachment(@Valid @RequestBody AttachmentDTO attachmentDTO) throws URISyntaxException {
        log.debug("REST request to save Attachment : {}", attachmentDTO);
        if (attachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new attachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttachmentDTO result = attachmentService.save(attachmentDTO);
        return ResponseEntity.created(new URI("/api/attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attachments : Updates an existing attachment.
     *
     * @param attachmentDTO the attachmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated attachmentDTO,
     * or with status 400 (Bad Request) if the attachmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the attachmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/attachments")
    public ResponseEntity<AttachmentDTO> updateAttachment(@Valid @RequestBody AttachmentDTO attachmentDTO) throws URISyntaxException {
        log.debug("REST request to update Attachment : {}", attachmentDTO);
        if (attachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttachmentDTO result = attachmentService.save(attachmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attachmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attachments : get all the attachments.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of attachments in body
     */
    @GetMapping("/attachments")
    public ResponseEntity<List<AttachmentDTO>> getAllAttachments(AttachmentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Attachments by criteria: {}", criteria);
        Page<AttachmentDTO> page = attachmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/attachments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /attachments/count : count all the attachments.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/attachments/count")
    public ResponseEntity<Long> countAttachments(AttachmentCriteria criteria) {
        log.debug("REST request to count Attachments by criteria: {}", criteria);
        return ResponseEntity.ok().body(attachmentQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /attachments/:id : get the "id" attachment.
     *
     * @param id the id of the attachmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the attachmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/attachments/{id}")
    public ResponseEntity<AttachmentDTO> getAttachment(@PathVariable Long id) {
        log.debug("REST request to get Attachment : {}", id);
        Optional<AttachmentDTO> attachmentDTO = attachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attachmentDTO);
    }

    /**
     * DELETE  /attachments/:id : delete the "id" attachment.
     *
     * @param id the id of the attachmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attachments/{id}")
    public ResponseEntity<Void> deleteAttachment(@PathVariable Long id) {
        log.debug("REST request to delete Attachment : {}", id);
        attachmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * POST  /attachments : Create a new attachment.
     *
     * @param entityId the related Entity
     * @param entityType the type of related Entity
     * @param file the attachment File to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attachmentDTO, or with status 400 (Bad Request) if the attachment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     *
     * TODO : test url http://localhost:8081/api/attachments/upload?entityId=1&entityType=CONTRACT
     * postman test:
     * var data = JSON.parse(responseBody);
     * postman.setEnvironmentVariable("token",data.id_token)
     */
    @RequestMapping(path = "/attachments/upload",
        consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE},
        method = RequestMethod.POST)
    public ResponseEntity<AttachmentDTO> uploadAttachment(@NotNull @RequestParam long entityId, @NotNull  @RequestParam EntityType entityType , @RequestPart MultipartFile file ) throws URISyntaxException, SystemException, IOException {

        AttachmentFileDTO attachmentDTO = new AttachmentFileDTO();
        attachmentDTO.setEntityId(entityId);
        attachmentDTO.setEntityType(entityType);
        attachmentDTO.setFile(file);
        attachmentDTO.buildAttachment();

        log.debug("REST request to save Attachment : {}", attachmentDTO);

        fileValidator.validateFile(store.getPath(attachmentDTO.getAttachment().getDiskFilename()),file);
        store.addFile(store.getTempPath( attachmentDTO.getAttachment().getDiskFilename() ),file.getOriginalFilename(),file.getInputStream(),false);

        AttachmentDTO result = attachmentService.save(attachmentDTO.getAttachment());
        if(result != null){
            //on deplace le fichier temporaire vers sa reele destination
            store.move(store.getTempPath( attachmentDTO.getAttachment().getDiskFilename() ), store.getPath(attachmentDTO.getAttachment().getDiskFilename()), file.getOriginalFilename());
        }
         //si ça se passe mal on revert la creation de la piece jointe
         store.deleteFile(store.getTempPath(attachmentDTO.getAttachment().getDiskFilename()), file.getOriginalFilename(),false);

        return ResponseEntity.created(new URI("/api/attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @RequestMapping(path = "/attachments/{id}",
        consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE},
        method = RequestMethod.POST)

    public ResponseEntity<AttachmentDTO> updateAttachment(@PathVariable Long id , @RequestPart MultipartFile file) throws SystemException, IOException {
        AttachmentDTO attachment = attachmentService.findOne(id).orElseThrow(AttachmentNotFoundException::new);
        String oldName = attachment.getFilename();
        AttachmentFileDTO attachmentDTO = new AttachmentFileDTO(attachment,file);
        attachmentDTO.buildAttachment();

        log.debug("REST request to save Attachment : {}", attachment);

        fileValidator.validateFile(store.getPath(attachmentDTO.getAttachment().getDiskFilename()),file);

        store.addFile(store.getTempPath( attachmentDTO.getAttachment().getDiskFilename() ),file.getOriginalFilename(),file.getInputStream(),false);
        AttachmentDTO result = attachmentService.save(attachmentDTO.getAttachment());
        if(result != null){
            //on supprime l'ancier
            store.deleteFile(attachmentDTO.getAttachment().getDiskFilename(),oldName);
            //on deplace le fichier temporaire vers sa reele destination
            store.move(store.getTempPath( attachmentDTO.getAttachment().getDiskFilename() ), store.getPath(attachmentDTO.getAttachment().getDiskFilename()), file.getOriginalFilename());
        }
        //si ça se passe mal on revert la creation de la piece jointe
        store.deleteFile(store.getTempPath(attachmentDTO.getAttachment().getDiskFilename()), file.getOriginalFilename(),false);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attachment.getId().toString()))
            .body(result);
    }

    @GetMapping("/attachments/{id}/download")
    public ResponseEntity<byte[]> downloadAttachment(@PathVariable Long id) throws SystemException {
        AttachmentDTO attachment = attachmentService.findOne(id).orElseThrow(AttachmentNotFoundException::new);
        attachment.setDownloads(attachment.getDownloads() + 1);
        attachmentService.save(attachment);

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(attachment.getContentType()))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFilename() + "\"")
            .body(store.getFileAsBytes(attachment.getDiskFilename(),attachment.getFilename()));
    }
}
