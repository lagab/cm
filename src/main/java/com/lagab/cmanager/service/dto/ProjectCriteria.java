package com.lagab.cmanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.lagab.cmanager.domain.enumeration.Visibility;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Project entity. This class is used in ProjectResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /projects?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProjectCriteria implements Serializable {
    /**
     * Class for filtering Visibility
     */
    public static class VisibilityFilter extends Filter<Visibility> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter archived;

    private VisibilityFilter visibility;

    private StringFilter path;

    private StringFilter description;

    private StringFilter imageUrl;

    private LongFilter workspaceId;

    private StringFilter workspacePath;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BooleanFilter getArchived() {
        return archived;
    }

    public void setArchived(BooleanFilter archived) {
        this.archived = archived;
    }

    public VisibilityFilter getVisibility() {
        return visibility;
    }

    public void setVisibility(VisibilityFilter visibility) {
        this.visibility = visibility;
    }

    public StringFilter getPath() {
        return path;
    }

    public void setPath(StringFilter path) {
        this.path = path;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LongFilter getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(LongFilter workspaceId) {
        this.workspaceId = workspaceId;
    }

    public StringFilter getWorkspacePath() {
        return workspacePath;
    }

    public void setWorkspacePath(StringFilter workspacePath) {
        this.workspacePath = workspacePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProjectCriteria that = (ProjectCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(archived, that.archived) &&
            Objects.equals(visibility, that.visibility) &&
            Objects.equals(path, that.path) &&
            Objects.equals(description, that.description) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(workspacePath, that.workspacePath) &&
            Objects.equals(workspaceId, that.workspaceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        archived,
        visibility,
        path,
        description,
        imageUrl,
        workspaceId,
        workspacePath
        );
    }

    @Override
    public String toString() {
        return "ProjectCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (archived != null ? "archived=" + archived + ", " : "") +
                (visibility != null ? "visibility=" + visibility + ", " : "") +
                (path != null ? "path=" + path + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
                (workspaceId != null ? "workspaceId=" + workspaceId + ", " : "") +
                (workspacePath != null ? "workspacePath=" + workspacePath + ", " : "") +
            "}";
    }

}
