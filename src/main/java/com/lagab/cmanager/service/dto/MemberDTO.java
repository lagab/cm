package com.lagab.cmanager.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.lagab.cmanager.domain.enumeration.SourceType;
import com.lagab.cmanager.domain.enumeration.AcessLevel;

/**
 * A DTO for the Member entity.
 */
public class MemberDTO implements Serializable {

    private Long id;

    private Instant expiresAt;

    @NotNull
    private SourceType type;

    @NotNull
    private Integer source;

    @NotNull
    private AcessLevel acessLevel;


    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public SourceType getType() {
        return type;
    }

    public void setType(SourceType type) {
        this.type = type;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public AcessLevel getAcessLevel() {
        return acessLevel;
    }

    public void setAcessLevel(AcessLevel acessLevel) {
        this.acessLevel = acessLevel;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MemberDTO memberDTO = (MemberDTO) o;
        if (memberDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), memberDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
            "id=" + getId() +
            ", expiresAt='" + getExpiresAt() + "'" +
            ", type='" + getType() + "'" +
            ", source=" + getSource() +
            ", acessLevel='" + getAcessLevel() + "'" +
            ", user=" + getUserId() +
            ", user='" + getUserLogin() + "'" +
            "}";
    }
}
