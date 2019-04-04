package com.lagab.cmanager.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Attachment entity.
 */
public class AttachmentDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private Long entityId;

    @NotNull
    private String entityType;

    @NotNull
    private String filename;

    @NotNull
    private String diskFilename;

    @NotNull
    private Integer fileSize;

    @NotNull
    private String contentType;

    @NotNull
    private Integer downloads;

    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDiskFilename() {
        return diskFilename;
    }

    public void setDiskFilename(String diskFilename) {
        this.diskFilename = diskFilename;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AttachmentDTO attachmentDTO = (AttachmentDTO) o;
        if (attachmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attachmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AttachmentDTO{" +
            "id=" + getId() +
            ", entityId=" + getEntityId() +
            ", entityType='" + getEntityType() + "'" +
            ", filename='" + getFilename() + "'" +
            ", diskFilename='" + getDiskFilename() + "'" +
            ", fileSize=" + getFileSize() +
            ", contentType='" + getContentType() + "'" +
            ", downloads=" + getDownloads() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
