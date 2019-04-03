package com.lagab.cmanager.repository;

import com.lagab.cmanager.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the User entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select distinct jhi_user from User jhi_user left join fetch jhi_user.authorities",
        countQuery = "select count(distinct jhi_user) from User jhi_user")
    Page<User> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct jhi_user from User jhi_user left join fetch jhi_user.authorities")
    List<User> findAllWithEagerRelationships();

    @Query("select jhi_user from User jhi_user left join fetch jhi_user.authorities where jhi_user.id =:id")
    Optional<User> findOneWithEagerRelationships(@Param("id") Long id);

}
