package com.lagab.cmanager.repository;

import com.lagab.cmanager.domain.Member;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Member entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {

    @Query("select jhi_member from Member jhi_member where jhi_member.user.login = ?#{principal.username}")
    List<Member> findByUserIsCurrentUser();


    /**
     * TODO : Create request who gives list of username's members
     */

}
