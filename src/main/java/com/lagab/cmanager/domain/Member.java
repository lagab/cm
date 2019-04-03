package com.lagab.cmanager.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.lagab.cmanager.domain.enumeration.SourceType;

import com.lagab.cmanager.domain.enumeration.AcessLevel;

/**
 * A Member.
 */
@Entity
@Table(name = "jhi_member")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private SourceType type;

    @NotNull
    @Column(name = "source", nullable = false)
    private Integer source;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "acess_level", nullable = false)
    private AcessLevel acessLevel;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("members")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public Member expiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public SourceType getType() {
        return type;
    }

    public Member type(SourceType type) {
        this.type = type;
        return this;
    }

    public void setType(SourceType type) {
        this.type = type;
    }

    public Integer getSource() {
        return source;
    }

    public Member source(Integer source) {
        this.source = source;
        return this;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public AcessLevel getAcessLevel() {
        return acessLevel;
    }

    public Member acessLevel(AcessLevel acessLevel) {
        this.acessLevel = acessLevel;
        return this;
    }

    public void setAcessLevel(AcessLevel acessLevel) {
        this.acessLevel = acessLevel;
    }

    public User getUser() {
        return user;
    }

    public Member user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Member member = (Member) o;
        if (member.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), member.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Member{" +
            "id=" + getId() +
            ", expiresAt='" + getExpiresAt() + "'" +
            ", type='" + getType() + "'" +
            ", source=" + getSource() +
            ", acessLevel='" + getAcessLevel() + "'" +
            "}";
    }
}
