package com.lagab.cmanager.service.mapper;

import com.lagab.cmanager.domain.*;
import com.lagab.cmanager.service.dto.AttachmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Attachment and its DTO AttachmentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AttachmentMapper extends EntityMapper<AttachmentDTO, Attachment> {



    default Attachment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Attachment attachment = new Attachment();
        attachment.setId(id);
        return attachment;
    }
}
