package com.lagab.cmanager.service.mapper;

import com.lagab.cmanager.domain.*;
import com.lagab.cmanager.service.dto.CommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Comment and its DTO CommentDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, ContractMapper.class})
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "contract.id", target = "contractId")
    @Mapping(source = "parent.id", target = "parentId")
    CommentDTO toDto(Comment comment);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "contractId", target = "contract")
    @Mapping(source = "parentId", target = "parent")
    @Mapping(target = "childs", ignore = true)
    Comment toEntity(CommentDTO commentDTO);

    default Comment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setId(id);
        return comment;
    }
}
