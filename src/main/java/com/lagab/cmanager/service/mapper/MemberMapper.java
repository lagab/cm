package com.lagab.cmanager.service.mapper;

import com.lagab.cmanager.domain.*;
import com.lagab.cmanager.service.dto.MemberDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Member and its DTO MemberDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface MemberMapper extends EntityMapper<MemberDTO, Member> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    MemberDTO toDto(Member member);

    @Mapping(source = "userId", target = "user")
    Member toEntity(MemberDTO memberDTO);

    default Member fromId(Long id) {
        if (id == null) {
            return null;
        }
        Member member = new Member();
        member.setId(id);
        return member;
    }
}
