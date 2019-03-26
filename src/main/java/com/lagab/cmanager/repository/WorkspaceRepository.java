package com.lagab.cmanager.repository;

import com.lagab.cmanager.domain.Workspace;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Workspace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    @Query("select workspace from Workspace workspace where workspace.owner.login = ?#{principal.username}")
    List<Workspace> findByOwnerIsCurrentUser();

}
