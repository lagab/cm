package com.lagab.cmanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the Comment entity. This class is used in CommentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /comments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommentCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter time;

    private LongFilter userId;

    private LongFilter contractId;

    private LongFilter parentId;

    private LongFilter childsId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getTime() {
        return time;
    }

    public void setTime(InstantFilter time) {
        this.time = time;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getContractId() {
        return contractId;
    }

    public void setContractId(LongFilter contractId) {
        this.contractId = contractId;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }

    public LongFilter getChildsId() {
        return childsId;
    }

    public void setChildsId(LongFilter childsId) {
        this.childsId = childsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommentCriteria that = (CommentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(time, that.time) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(contractId, that.contractId) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(childsId, that.childsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        time,
        userId,
        contractId,
        parentId,
        childsId
        );
    }

    @Override
    public String toString() {
        return "CommentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (time != null ? "time=" + time + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (contractId != null ? "contractId=" + contractId + ", " : "") +
                (parentId != null ? "parentId=" + parentId + ", " : "") +
                (childsId != null ? "childsId=" + childsId + ", " : "") +
            "}";
    }

}
