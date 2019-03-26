package com.lagab.cmanager.web.rest;

import com.lagab.cmanager.CmApp;

import com.lagab.cmanager.domain.Member;
import com.lagab.cmanager.domain.User;
import com.lagab.cmanager.repository.MemberRepository;
import com.lagab.cmanager.service.MemberService;
import com.lagab.cmanager.service.dto.MemberDTO;
import com.lagab.cmanager.service.mapper.MemberMapper;
import com.lagab.cmanager.web.rest.errors.ExceptionTranslator;
import com.lagab.cmanager.service.dto.MemberCriteria;
import com.lagab.cmanager.service.MemberQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.lagab.cmanager.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lagab.cmanager.domain.enumeration.SourceType;
import com.lagab.cmanager.domain.enumeration.AcessLevel;
/**
 * Test class for the MemberResource REST controller.
 *
 * @see MemberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmApp.class)
public class MemberResourceIntTest {

    private static final Instant DEFAULT_EXPIRES_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRES_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final SourceType DEFAULT_TYPE = SourceType.WORKSPACE;
    private static final SourceType UPDATED_TYPE = SourceType.PROJECT;

    private static final Integer DEFAULT_SOURCE = 1;
    private static final Integer UPDATED_SOURCE = 2;

    private static final AcessLevel DEFAULT_ACESS_LEVEL = AcessLevel.GUEST;
    private static final AcessLevel UPDATED_ACESS_LEVEL = AcessLevel.CONTRIBUTOR;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberQueryService memberQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restMemberMockMvc;

    private Member member;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MemberResource memberResource = new MemberResource(memberService, memberQueryService);
        this.restMemberMockMvc = MockMvcBuilders.standaloneSetup(memberResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Member createEntity(EntityManager em) {
        Member member = new Member()
            .expiresAt(DEFAULT_EXPIRES_AT)
            .type(DEFAULT_TYPE)
            .source(DEFAULT_SOURCE)
            .acessLevel(DEFAULT_ACESS_LEVEL);
        // Add required entity
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        member.setUser(user);
        return member;
    }

    @Before
    public void initTest() {
        member = createEntity(em);
    }

    @Test
    @Transactional
    public void createMember() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);
        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate + 1);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getExpiresAt()).isEqualTo(DEFAULT_EXPIRES_AT);
        assertThat(testMember.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMember.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testMember.getAcessLevel()).isEqualTo(DEFAULT_ACESS_LEVEL);
    }

    @Test
    @Transactional
    public void createMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // Create the Member with an existing ID
        member.setId(1L);
        MemberDTO memberDTO = memberMapper.toDto(member);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setType(null);

        // Create the Member, which fails.
        MemberDTO memberDTO = memberMapper.toDto(member);

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSourceIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setSource(null);

        // Create the Member, which fails.
        MemberDTO memberDTO = memberMapper.toDto(member);

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAcessLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = memberRepository.findAll().size();
        // set the field null
        member.setAcessLevel(null);

        // Create the Member, which fails.
        MemberDTO memberDTO = memberMapper.toDto(member);

        restMemberMockMvc.perform(post("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMembers() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList
        restMemberMockMvc.perform(get("/api/members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
            .andExpect(jsonPath("$.[*].expiresAt").value(hasItem(DEFAULT_EXPIRES_AT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].acessLevel").value(hasItem(DEFAULT_ACESS_LEVEL.toString())));
    }
    
    @Test
    @Transactional
    public void getMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", member.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(member.getId().intValue()))
            .andExpect(jsonPath("$.expiresAt").value(DEFAULT_EXPIRES_AT.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.acessLevel").value(DEFAULT_ACESS_LEVEL.toString()));
    }

    @Test
    @Transactional
    public void getAllMembersByExpiresAtIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where expiresAt equals to DEFAULT_EXPIRES_AT
        defaultMemberShouldBeFound("expiresAt.equals=" + DEFAULT_EXPIRES_AT);

        // Get all the memberList where expiresAt equals to UPDATED_EXPIRES_AT
        defaultMemberShouldNotBeFound("expiresAt.equals=" + UPDATED_EXPIRES_AT);
    }

    @Test
    @Transactional
    public void getAllMembersByExpiresAtIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where expiresAt in DEFAULT_EXPIRES_AT or UPDATED_EXPIRES_AT
        defaultMemberShouldBeFound("expiresAt.in=" + DEFAULT_EXPIRES_AT + "," + UPDATED_EXPIRES_AT);

        // Get all the memberList where expiresAt equals to UPDATED_EXPIRES_AT
        defaultMemberShouldNotBeFound("expiresAt.in=" + UPDATED_EXPIRES_AT);
    }

    @Test
    @Transactional
    public void getAllMembersByExpiresAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where expiresAt is not null
        defaultMemberShouldBeFound("expiresAt.specified=true");

        // Get all the memberList where expiresAt is null
        defaultMemberShouldNotBeFound("expiresAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where type equals to DEFAULT_TYPE
        defaultMemberShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the memberList where type equals to UPDATED_TYPE
        defaultMemberShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMembersByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultMemberShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the memberList where type equals to UPDATED_TYPE
        defaultMemberShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllMembersByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where type is not null
        defaultMemberShouldBeFound("type.specified=true");

        // Get all the memberList where type is null
        defaultMemberShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where source equals to DEFAULT_SOURCE
        defaultMemberShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the memberList where source equals to UPDATED_SOURCE
        defaultMemberShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllMembersBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultMemberShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the memberList where source equals to UPDATED_SOURCE
        defaultMemberShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllMembersBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where source is not null
        defaultMemberShouldBeFound("source.specified=true");

        // Get all the memberList where source is null
        defaultMemberShouldNotBeFound("source.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersBySourceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where source greater than or equals to DEFAULT_SOURCE
        defaultMemberShouldBeFound("source.greaterOrEqualThan=" + DEFAULT_SOURCE);

        // Get all the memberList where source greater than or equals to UPDATED_SOURCE
        defaultMemberShouldNotBeFound("source.greaterOrEqualThan=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    public void getAllMembersBySourceIsLessThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where source less than or equals to DEFAULT_SOURCE
        defaultMemberShouldNotBeFound("source.lessThan=" + DEFAULT_SOURCE);

        // Get all the memberList where source less than or equals to UPDATED_SOURCE
        defaultMemberShouldBeFound("source.lessThan=" + UPDATED_SOURCE);
    }


    @Test
    @Transactional
    public void getAllMembersByAcessLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where acessLevel equals to DEFAULT_ACESS_LEVEL
        defaultMemberShouldBeFound("acessLevel.equals=" + DEFAULT_ACESS_LEVEL);

        // Get all the memberList where acessLevel equals to UPDATED_ACESS_LEVEL
        defaultMemberShouldNotBeFound("acessLevel.equals=" + UPDATED_ACESS_LEVEL);
    }

    @Test
    @Transactional
    public void getAllMembersByAcessLevelIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where acessLevel in DEFAULT_ACESS_LEVEL or UPDATED_ACESS_LEVEL
        defaultMemberShouldBeFound("acessLevel.in=" + DEFAULT_ACESS_LEVEL + "," + UPDATED_ACESS_LEVEL);

        // Get all the memberList where acessLevel equals to UPDATED_ACESS_LEVEL
        defaultMemberShouldNotBeFound("acessLevel.in=" + UPDATED_ACESS_LEVEL);
    }

    @Test
    @Transactional
    public void getAllMembersByAcessLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where acessLevel is not null
        defaultMemberShouldBeFound("acessLevel.specified=true");

        // Get all the memberList where acessLevel is null
        defaultMemberShouldNotBeFound("acessLevel.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembersByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIntTest.createEntity(em);
        em.persist(user);
        em.flush();
        member.setUser(user);
        memberRepository.saveAndFlush(member);
        Long userId = user.getId();

        // Get all the memberList where user equals to userId
        defaultMemberShouldBeFound("userId.equals=" + userId);

        // Get all the memberList where user equals to userId + 1
        defaultMemberShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultMemberShouldBeFound(String filter) throws Exception {
        restMemberMockMvc.perform(get("/api/members?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
            .andExpect(jsonPath("$.[*].expiresAt").value(hasItem(DEFAULT_EXPIRES_AT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].acessLevel").value(hasItem(DEFAULT_ACESS_LEVEL.toString())));

        // Check, that the count call also returns 1
        restMemberMockMvc.perform(get("/api/members/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultMemberShouldNotBeFound(String filter) throws Exception {
        restMemberMockMvc.perform(get("/api/members?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMemberMockMvc.perform(get("/api/members/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMember() throws Exception {
        // Get the member
        restMemberMockMvc.perform(get("/api/members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member
        Member updatedMember = memberRepository.findById(member.getId()).get();
        // Disconnect from session so that the updates on updatedMember are not directly saved in db
        em.detach(updatedMember);
        updatedMember
            .expiresAt(UPDATED_EXPIRES_AT)
            .type(UPDATED_TYPE)
            .source(UPDATED_SOURCE)
            .acessLevel(UPDATED_ACESS_LEVEL);
        MemberDTO memberDTO = memberMapper.toDto(updatedMember);

        restMemberMockMvc.perform(put("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getExpiresAt()).isEqualTo(UPDATED_EXPIRES_AT);
        assertThat(testMember.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMember.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testMember.getAcessLevel()).isEqualTo(UPDATED_ACESS_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberMockMvc.perform(put("/api/members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeDelete = memberRepository.findAll().size();

        // Delete the member
        restMemberMockMvc.perform(delete("/api/members/{id}", member.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Member.class);
        Member member1 = new Member();
        member1.setId(1L);
        Member member2 = new Member();
        member2.setId(member1.getId());
        assertThat(member1).isEqualTo(member2);
        member2.setId(2L);
        assertThat(member1).isNotEqualTo(member2);
        member1.setId(null);
        assertThat(member1).isNotEqualTo(member2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberDTO.class);
        MemberDTO memberDTO1 = new MemberDTO();
        memberDTO1.setId(1L);
        MemberDTO memberDTO2 = new MemberDTO();
        assertThat(memberDTO1).isNotEqualTo(memberDTO2);
        memberDTO2.setId(memberDTO1.getId());
        assertThat(memberDTO1).isEqualTo(memberDTO2);
        memberDTO2.setId(2L);
        assertThat(memberDTO1).isNotEqualTo(memberDTO2);
        memberDTO1.setId(null);
        assertThat(memberDTO1).isNotEqualTo(memberDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(memberMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(memberMapper.fromId(null)).isNull();
    }
}
