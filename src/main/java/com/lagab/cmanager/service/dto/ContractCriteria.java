package com.lagab.cmanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.lagab.cmanager.domain.enumeration.Status;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Contract entity. This class is used in ContractResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /contracts?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ContractCriteria implements Serializable {
    /**
     * Class for filtering Status
     */
    public static class StatusFilter extends Filter<Status> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private StringFilter subject;

    private InstantFilter expiresAt;

    private StatusFilter status;

    private BooleanFilter template;

    private LocalDateFilter reminder;

    private LongFilter projectId;

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

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getSubject() {
        return subject;
    }

    public void setSubject(StringFilter subject) {
        this.subject = subject;
    }

    public InstantFilter getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(InstantFilter expiresAt) {
        this.expiresAt = expiresAt;
    }

    public StatusFilter getStatus() {
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
    }

    public BooleanFilter getTemplate() {
        return template;
    }

    public void setTemplate(BooleanFilter template) {
        this.template = template;
    }

    public LocalDateFilter getReminder() {
        return reminder;
    }

    public void setReminder(LocalDateFilter reminder) {
        this.reminder = reminder;
    }

    public LongFilter getProjectId() {
        return projectId;
    }

    public void setProjectId(LongFilter projectId) {
        this.projectId = projectId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ContractCriteria that = (ContractCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(subject, that.subject) &&
            Objects.equals(expiresAt, that.expiresAt) &&
            Objects.equals(status, that.status) &&
            Objects.equals(template, that.template) &&
            Objects.equals(reminder, that.reminder) &&
            Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        description,
        subject,
        expiresAt,
        status,
        template,
        reminder,
        projectId
        );
    }

    @Override
    public String toString() {
        return "ContractCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (subject != null ? "subject=" + subject + ", " : "") +
                (expiresAt != null ? "expiresAt=" + expiresAt + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (template != null ? "template=" + template + ", " : "") +
                (reminder != null ? "reminder=" + reminder + ", " : "") +
                (projectId != null ? "projectId=" + projectId + ", " : "") +
            "}";
    }

}
