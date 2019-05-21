package com.lagab.cmanager.service.dto;

import com.lagab.cmanager.domain.enumeration.EntityType;
import com.lagab.cmanager.web.rest.util.StringConstants;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the Attachment entity.
 */
public class AttachmentFileDTO extends AbstractAuditingDTO implements Serializable {

    @NotNull
    private Long entityId;

    @NotNull
    private EntityType entityType;

    @NotNull
    private MultipartFile file;

    private String description;

    private AttachmentDTO attachment;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AttachmentDTO getAttachment() {
        return attachment;
    }

    public void setAttachment(AttachmentDTO attachment) {
        this.attachment = attachment;
    }

    public void buildAttachment() {
        this.attachment = new AttachmentDTO();
        attachment.setFilename(file.getName());
        attachment.setEntityId(entityId);
        attachment.setEntityType(entityType);
        attachment.setContentType(file.getContentType());
        attachment.setFileSize(file.getSize());
        attachment.setDownloads(0);
        attachment.setDiskFilename(entityType.toString().toLowerCase() + StringConstants.SLASH + entityId );
    }

    @Override
    public String toString() {
        return "AttachmentDTO{" +
            "entityId=" + getEntityId() +
            ", entityType='" + getEntityType() + "'" +
            ", file='" + getFile().toString() + "'" +
            ", attachement='" + getAttachment() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }

}
