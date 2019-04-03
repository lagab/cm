package com.lagab.cmanager.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2)
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "jhi_time", nullable = false)
    private Instant time;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("comments")
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("comments")
    private Contract contract;

    @ManyToOne
    @JsonIgnoreProperties("comments")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> childs = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Comment content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getTime() {
        return time;
    }

    public Comment time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public Comment user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Contract getContract() {
        return contract;
    }

    public Comment contract(Contract contract) {
        this.contract = contract;
        return this;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Comment getParent() {
        return parent;
    }

    public Comment parent(Comment comment) {
        this.parent = comment;
        return this;
    }

    public void setParent(Comment comment) {
        this.parent = comment;
    }

    public Set<Comment> getChilds() {
        return childs;
    }

    public Comment childs(Set<Comment> comments) {
        this.childs = comments;
        return this;
    }

    public Comment addChilds(Comment comment) {
        this.childs.add(comment);
        comment.setParent(this);
        return this;
    }

    public Comment removeChilds(Comment comment) {
        this.childs.remove(comment);
        comment.setParent(null);
        return this;
    }

    public void setChilds(Set<Comment> comments) {
        this.childs = comments;
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
        Comment comment = (Comment) o;
        if (comment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
}
