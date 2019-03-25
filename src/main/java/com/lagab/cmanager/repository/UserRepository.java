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

    @Query(value = "select distinct user from User user left join fetch user.authorities",
        countQuery = "select count(distinct user) from User user")
    Page<User> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct user from User user left join fetch user.authorities")
    List<User> findAllWithEagerRelationships();

    @Query("select user from User user left join fetch user.authorities where user.id =:id")
    Optional<User> findOneWithEagerRelationships(@Param("id") Long id);

}
