package com.lagab.cmanager.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.lagab.cmanager.domain.Member;
import com.lagab.cmanager.domain.*; // for static metamodels
import com.lagab.cmanager.repository.MemberRepository;
import com.lagab.cmanager.service.dto.MemberCriteria;
import com.lagab.cmanager.service.dto.MemberDTO;
import com.lagab.cmanager.service.mapper.MemberMapper;

/**
 * Service for executing complex queries for Member entities in the database.
 * The main input is a {@link MemberCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MemberDTO} or a {@link Page} of {@link MemberDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MemberQueryService extends QueryService<Member> {

    private final Logger log = LoggerFactory.getLogger(MemberQueryService.class);

    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;

    public MemberQueryService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    /**
     * Return a {@link List} of {@link MemberDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MemberDTO> findByCriteria(MemberCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Member> specification = createSpecification(criteria);
        return memberMapper.toDto(memberRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MemberDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberDTO> findByCriteria(MemberCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Member> specification = createSpecification(criteria);
        return memberRepository.findAll(specification, page)
            .map(memberMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MemberCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Member> specification = createSpecification(criteria);
        return memberRepository.count(specification);
    }

    /**
     * Function to convert MemberCriteria to a {@link Specification}
     */
    private Specification<Member> createSpecification(MemberCriteria criteria) {
        Specification<Member> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Member_.id));
            }
            if (criteria.getExpiresAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiresAt(), Member_.expiresAt));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Member_.type));
            }
            if (criteria.getSource() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSource(), Member_.source));
            }
            if (criteria.getAcessLevel() != null) {
                specification = specification.and(buildSpecification(criteria.getAcessLevel(), Member_.acessLevel));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(Member_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
