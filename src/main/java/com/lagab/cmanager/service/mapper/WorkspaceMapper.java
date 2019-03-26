package com.lagab.cmanager.service.mapper;

import com.lagab.cmanager.domain.*;
import com.lagab.cmanager.service.dto.WorkspaceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Workspace and its DTO WorkspaceDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface WorkspaceMapper extends EntityMapper<WorkspaceDTO, Workspace> {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.login", target = "ownerLogin")
    WorkspaceDTO toDto(Workspace workspace);

    @Mapping(source = "ownerId", target = "owner")
    Workspace toEntity(WorkspaceDTO workspaceDTO);

    default Workspace fromId(Long id) {
        if (id == null) {
            return null;
        }
        Workspace workspace = new Workspace();
        workspace.setId(id);
        return workspace;
    }
}
