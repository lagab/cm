package com.lagab.cmanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.lagab.cmanager.domain.enumeration.EntityType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Attachment entity. This class is used in AttachmentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /attachments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AttachmentCriteria implements Serializable {
    /**
     * Class for filtering EntityType
     */
    public static class EntityTypeFilter extends Filter<EntityType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter entityId;

    private EntityTypeFilter entityType;

    private StringFilter filename;

    private StringFilter diskFilename;

    private LongFilter fileSize;

    private StringFilter contentType;

    private IntegerFilter downloads;

    private StringFilter description;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getEntityId() {
        return entityId;
    }

    public void setEntityId(LongFilter entityId) {
        this.entityId = entityId;
    }

    public EntityTypeFilter getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityTypeFilter entityType) {
        this.entityType = entityType;
    }

    public StringFilter getFilename() {
        return filename;
    }

    public void setFilename(StringFilter filename) {
        this.filename = filename;
    }

    public StringFilter getDiskFilename() {
        return diskFilename;
    }

    public void setDiskFilename(StringFilter diskFilename) {
        this.diskFilename = diskFilename;
    }

    public LongFilter getFileSize() {
        return fileSize;
    }

    public void setFileSize(LongFilter fileSize) {
        this.fileSize = fileSize;
    }

    public StringFilter getContentType() {
        return contentType;
    }

    public void setContentType(StringFilter contentType) {
        this.contentType = contentType;
    }

    public IntegerFilter getDownloads() {
        return downloads;
    }

    public void setDownloads(IntegerFilter downloads) {
        this.downloads = downloads;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
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
        final AttachmentCriteria that = (AttachmentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(entityId, that.entityId) &&
            Objects.equals(entityType, that.entityType) &&
            Objects.equals(filename, that.filename) &&
            Objects.equals(diskFilename, that.diskFilename) &&
            Objects.equals(fileSize, that.fileSize) &&
            Objects.equals(contentType, that.contentType) &&
            Objects.equals(downloads, that.downloads) &&
            Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        entityId,
        entityType,
        filename,
        diskFilename,
        fileSize,
        contentType,
        downloads,
        description
        );
    }

    @Override
    public String toString() {
        return "AttachmentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (entityId != null ? "entityId=" + entityId + ", " : "") +
                (entityType != null ? "entityType=" + entityType + ", " : "") +
                (filename != null ? "filename=" + filename + ", " : "") +
                (diskFilename != null ? "diskFilename=" + diskFilename + ", " : "") +
                (fileSize != null ? "fileSize=" + fileSize + ", " : "") +
                (contentType != null ? "contentType=" + contentType + ", " : "") +
                (downloads != null ? "downloads=" + downloads + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
            "}";
    }

}
