package com.lagab.cmanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.lagab.cmanager.domain.enumeration.SourceType;
import com.lagab.cmanager.domain.enumeration.AcessLevel;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the Member entity. This class is used in MemberResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /members?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MemberCriteria implements Serializable {
    /**
     * Class for filtering SourceType
     */
    public static class SourceTypeFilter extends Filter<SourceType> {
    }
    /**
     * Class for filtering AcessLevel
     */
    public static class AcessLevelFilter extends Filter<AcessLevel> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter expiresAt;

    private SourceTypeFilter type;

    private IntegerFilter source;

    private AcessLevelFilter acessLevel;

    private LongFilter userId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(InstantFilter expiresAt) {
        this.expiresAt = expiresAt;
    }

    public SourceTypeFilter getType() {
        return type;
    }

    public void setType(SourceTypeFilter type) {
        this.type = type;
    }

    public IntegerFilter getSource() {
        return source;
    }

    public void setSource(IntegerFilter source) {
        this.source = source;
    }

    public AcessLevelFilter getAcessLevel() {
        return acessLevel;
    }

    public void setAcessLevel(AcessLevelFilter acessLevel) {
        this.acessLevel = acessLevel;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MemberCriteria that = (MemberCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(expiresAt, that.expiresAt) &&
            Objects.equals(type, that.type) &&
            Objects.equals(source, that.source) &&
            Objects.equals(acessLevel, that.acessLevel) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        expiresAt,
        type,
        source,
        acessLevel,
        userId
        );
    }

    @Override
    public String toString() {
        return "MemberCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (expiresAt != null ? "expiresAt=" + expiresAt + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (source != null ? "source=" + source + ", " : "") +
                (acessLevel != null ? "acessLevel=" + acessLevel + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
