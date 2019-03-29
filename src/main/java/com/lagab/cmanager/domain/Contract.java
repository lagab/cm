package com.lagab.cmanager.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.lagab.cmanager.domain.enumeration.Status;

/**
 * A Contract.
 */
@Entity
@Table(name = "contract")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contract extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "subject")
    private String subject;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Lob
    @Column(name = "configuration")
    private String configuration;

    @Lob
    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "template")
    private Boolean template;

    @ManyToOne
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Contract name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Contract description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject() {
        return subject;
    }

    public Contract subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public Contract expiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getConfiguration() {
        return configuration;
    }

    public Contract configuration(String configuration) {
        this.configuration = configuration;
        return this;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public String getContent() {
        return content;
    }

    public Contract content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Status getStatus() {
        return status;
    }

    public Contract status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean isTemplate() {
        return template;
    }

    public Contract template(Boolean template) {
        this.template = template;
        return this;
    }

    public void setTemplate(Boolean template) {
        this.template = template;
    }

    public Project getProject() {
        return project;
    }

    public Contract project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
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
        Contract contract = (Contract) o;
        if (contract.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contract.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contract{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", subject='" + getSubject() + "'" +
            ", expiresAt='" + getExpiresAt() + "'" +
            ", configuration='" + getConfiguration() + "'" +
            ", content='" + getContent() + "'" +
            ", status='" + getStatus() + "'" +
            ", template='" + isTemplate() + "'" +
            "}";
    }
}
