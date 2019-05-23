package com.lagab.cmanager.service.impl;

import com.lagab.cmanager.service.AttachmentService;
import com.lagab.cmanager.domain.Attachment;
import com.lagab.cmanager.repository.AttachmentRepository;
import com.lagab.cmanager.service.dto.AttachmentDTO;
import com.lagab.cmanager.service.mapper.AttachmentMapper;
import com.lagab.cmanager.store.Store;
import com.lagab.cmanager.web.rest.errors.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Attachment.
 */
@Service
@Transactional
public class AttachmentServiceImpl implements AttachmentService {

    private final Logger log = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    private final AttachmentRepository attachmentRepository;

    private final AttachmentMapper attachmentMapper;

    private final Store store;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, AttachmentMapper attachmentMapper, Store store) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentMapper = attachmentMapper;
        this.store = store;
    }

    /**
     * Save a attachment.
     *
     * @param attachmentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AttachmentDTO save(AttachmentDTO attachmentDTO) {
        log.debug("Request to save Attachment : {}", attachmentDTO);
        Attachment attachment = attachmentMapper.toEntity(attachmentDTO);
        attachment = attachmentRepository.save(attachment);
        return attachmentMapper.toDto(attachment);
    }

    /**
     * Get all the attachments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AttachmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Attachments");
        return attachmentRepository.findAll(pageable)
            .map(attachmentMapper::toDto);
    }


    /**
     * Get one attachment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AttachmentDTO> findOne(Long id) {
        log.debug("Request to get Attachment : {}", id);
        return attachmentRepository.findById(id)
            .map(attachmentMapper::toDto);
    }

    /**
     * Delete the attachment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Attachment : {}", id);
        Attachment attachment = attachmentRepository.findById(id).get();
        try {
            store.deleteFile(attachment.getDiskFilename(),attachment.getFilename());
        } catch (SystemException e) {
            log.error(e.getMessage(),e);
        }
        attachmentRepository.deleteById(id);
    }
}
