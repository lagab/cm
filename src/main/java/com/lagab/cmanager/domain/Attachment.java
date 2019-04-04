package com.lagab.cmanager.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Attachment.
 */
@Entity
@Table(name = "attachment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Attachment extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @NotNull
    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @NotNull
    @Column(name = "filename", nullable = false)
    private String filename;

    @NotNull
    @Column(name = "disk_filename", nullable = false)
    private String diskFilename;

    @NotNull
    @Column(name = "file_size", nullable = false)
    private Integer fileSize;

    @NotNull
    @Column(name = "content_type", nullable = false)
    private String contentType;

    @NotNull
    @Column(name = "downloads", nullable = false)
    private Integer downloads;

    @Column(name = "description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntityId() {
        return entityId;
    }

    public Attachment entityId(Long entityId) {
        this.entityId = entityId;
        return this;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public Attachment entityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getFilename() {
        return filename;
    }

    public Attachment filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDiskFilename() {
        return diskFilename;
    }

    public Attachment diskFilename(String diskFilename) {
        this.diskFilename = diskFilename;
        return this;
    }

    public void setDiskFilename(String diskFilename) {
        this.diskFilename = diskFilename;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public Attachment fileSize(Integer fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public Attachment contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public Attachment downloads(Integer downloads) {
        this.downloads = downloads;
        return this;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public String getDescription() {
        return description;
    }

    public Attachment description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attachment attachment = (Attachment) o;
        if (attachment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attachment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Attachment{" +
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
