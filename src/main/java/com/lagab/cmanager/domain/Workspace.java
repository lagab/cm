package com.lagab.cmanager.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.lagab.cmanager.domain.enumeration.Visibility;

/**
 * A Workspace.
 */
@Entity
@Table(name = "workspace")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Workspace implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 256)
    @Column(name = "description", length = 256)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private Visibility visibility;

    @NotNull
    @Size(max = 50)
    @Pattern(regexp = "^[a-z0-9-]*$")
    @Column(name = "path", length = 50, nullable = false, unique = true)
    private String path;

    @ManyToOne
    @JsonIgnoreProperties("workspaces")
    private User owner;

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

    public Workspace name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Workspace description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public Workspace visibility(Visibility visibility) {
        this.visibility = visibility;
        return this;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public String getPath() {
        return path;
    }

    public Workspace path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public User getOwner() {
        return owner;
    }

    public Workspace owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
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
        Workspace workspace = (Workspace) o;
        if (workspace.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workspace.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Workspace{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", visibility='" + getVisibility() + "'" +
            ", path='" + getPath() + "'" +
            "}";
    }
}
