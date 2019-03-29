package com.lagab.cmanager.web.rest;

import com.lagab.cmanager.CmApp;

import com.lagab.cmanager.domain.Contract;
import com.lagab.cmanager.domain.Project;
import com.lagab.cmanager.repository.ContractRepository;
import com.lagab.cmanager.service.ContractService;
import com.lagab.cmanager.service.dto.ContractDTO;
import com.lagab.cmanager.service.mapper.ContractMapper;
import com.lagab.cmanager.web.rest.errors.ExceptionTranslator;
import com.lagab.cmanager.service.dto.ContractCriteria;
import com.lagab.cmanager.service.ContractQueryService;

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
import org.springframework.util.Base64Utils;
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

import com.lagab.cmanager.domain.enumeration.Status;
/**
 * Test class for the ContractResource REST controller.
 *
 * @see ContractResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmApp.class)
public class ContractResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXPIRES_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRES_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CONFIGURATION = "AAAAAAAAAA";
    private static final String UPDATED_CONFIGURATION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.DRAFT;
    private static final Status UPDATED_STATUS = Status.PENDING;

    private static final Boolean DEFAULT_TEMPLATE = false;
    private static final Boolean UPDATED_TEMPLATE = true;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractQueryService contractQueryService;

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

    private MockMvc restContractMockMvc;

    private Contract contract;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContractResource contractResource = new ContractResource(contractService, contractQueryService);
        this.restContractMockMvc = MockMvcBuilders.standaloneSetup(contractResource)
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
    public static Contract createEntity(EntityManager em) {
        Contract contract = new Contract()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .subject(DEFAULT_SUBJECT)
            .expiresAt(DEFAULT_EXPIRES_AT)
            .configuration(DEFAULT_CONFIGURATION)
            .content(DEFAULT_CONTENT)
            .status(DEFAULT_STATUS)
            .template(DEFAULT_TEMPLATE);
        return contract;
    }

    @Before
    public void initTest() {
        contract = createEntity(em);
    }

    @Test
    @Transactional
    public void createContract() throws Exception {
        int databaseSizeBeforeCreate = contractRepository.findAll().size();

        // Create the Contract
        ContractDTO contractDTO = contractMapper.toDto(contract);
        restContractMockMvc.perform(post("/api/contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractDTO)))
            .andExpect(status().isCreated());

        // Validate the Contract in the database
        List<Contract> contractList = contractRepository.findAll();
        assertThat(contractList).hasSize(databaseSizeBeforeCreate + 1);
        Contract testContract = contractList.get(contractList.size() - 1);
        assertThat(testContract.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContract.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testContract.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testContract.getExpiresAt()).isEqualTo(DEFAULT_EXPIRES_AT);
        assertThat(testContract.getConfiguration()).isEqualTo(DEFAULT_CONFIGURATION);
        assertThat(testContract.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testContract.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testContract.isTemplate()).isEqualTo(DEFAULT_TEMPLATE);
    }

    @Test
    @Transactional
    public void createContractWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contractRepository.findAll().size();

        // Create the Contract with an existing ID
        contract.setId(1L);
        ContractDTO contractDTO = contractMapper.toDto(contract);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractMockMvc.perform(post("/api/contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contract in the database
        List<Contract> contractList = contractRepository.findAll();
        assertThat(contractList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contractRepository.findAll().size();
        // set the field null
        contract.setName(null);

        // Create the Contract, which fails.
        ContractDTO contractDTO = contractMapper.toDto(contract);

        restContractMockMvc.perform(post("/api/contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractDTO)))
            .andExpect(status().isBadRequest());

        List<Contract> contractList = contractRepository.findAll();
        assertThat(contractList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContracts() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList
        restContractMockMvc.perform(get("/api/contracts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contract.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].expiresAt").value(hasItem(DEFAULT_EXPIRES_AT.toString())))
            .andExpect(jsonPath("$.[*].configuration").value(hasItem(DEFAULT_CONFIGURATION.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].template").value(hasItem(DEFAULT_TEMPLATE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getContract() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get the contract
        restContractMockMvc.perform(get("/api/contracts/{id}", contract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contract.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.expiresAt").value(DEFAULT_EXPIRES_AT.toString()))
            .andExpect(jsonPath("$.configuration").value(DEFAULT_CONFIGURATION.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.template").value(DEFAULT_TEMPLATE.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllContractsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList where name equals to DEFAULT_NAME
        defaultContractShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the contractList where name equals to UPDATED_NAME
        defaultContractShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContractsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList where name in DEFAULT_NAME or UPDATED_NAME
        defaultContractShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the contractList where name equals to UPDATED_NAME
        defaultContractShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllContractsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList where name is not null
        defaultContractShouldBeFound("name.specified=true");

        // Get all the contractList where name is null
        defaultContractShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList where description equals to DEFAULT_DESCRIPTION
        defaultContractShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the contractList where description equals to UPDATED_DESCRIPTION
        defaultContractShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllContractsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultContractShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the contractList where description equals to UPDATED_DESCRIPTION
        defaultContractShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllContractsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList where description is not null
        defaultContractShouldBeFound("description.specified=true");

        // Get all the contractList where description is null
        defaultContractShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractsBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList where subject equals to DEFAULT_SUBJECT
        defaultContractShouldBeFound("subject.equals=" + DEFAULT_SUBJECT);

        // Get all the contractList where subject equals to UPDATED_SUBJECT
        defaultContractShouldNotBeFound("subject.equals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllContractsBySubjectIsInShouldWork() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList where subject in DEFAULT_SUBJECT or UPDATED_SUBJECT
        defaultContractShouldBeFound("subject.in=" + DEFAULT_SUBJECT + "," + UPDATED_SUBJECT);

        // Get all the contractList where subject equals to UPDATED_SUBJECT
        defaultContractShouldNotBeFound("subject.in=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllContractsBySubjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList where subject is not null
        defaultContractShouldBeFound("subject.specified=true");

        // Get all the contractList where subject is null
        defaultContractShouldNotBeFound("subject.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractsByExpiresAtIsEqualToSomething() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList where expiresAt equals to DEFAULT_EXPIRES_AT
        defaultContractShouldBeFound("expiresAt.equals=" + DEFAULT_EXPIRES_AT);

        // Get all the contractList where expiresAt equals to UPDATED_EXPIRES_AT
        defaultContractShouldNotBeFound("expiresAt.equals=" + UPDATED_EXPIRES_AT);
    }

    @Test
    @Transactional
    public void getAllContractsByExpiresAtIsInShouldWork() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList where expiresAt in DEFAULT_EXPIRES_AT or UPDATED_EXPIRES_AT
        defaultContractShouldBeFound("expiresAt.in=" + DEFAULT_EXPIRES_AT + "," + UPDATED_EXPIRES_AT);

        // Get all the contractList where expiresAt equals to UPDATED_EXPIRES_AT
        defaultContractShouldNotBeFound("expiresAt.in=" + UPDATED_EXPIRES_AT);
    }

    @Test
    @Transactional
    public void getAllContractsByExpiresAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList where expiresAt is not null
        defaultContractShouldBeFound("expiresAt.specified=true");

        // Get all the contractList where expiresAt is null
        defaultContractShouldNotBeFound("expiresAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList where status equals to DEFAULT_STATUS
        defaultContractShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the contractList where status equals to UPDATED_STATUS
        defaultContractShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllContractsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultContractShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the contractList where status equals to UPDATED_STATUS
        defaultContractShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllContractsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList where status is not null
        defaultContractShouldBeFound("status.specified=true");

        // Get all the contractList where status is null
        defaultContractShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractsByTemplateIsEqualToSomething() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList where template equals to DEFAULT_TEMPLATE
        defaultContractShouldBeFound("template.equals=" + DEFAULT_TEMPLATE);

        // Get all the contractList where template equals to UPDATED_TEMPLATE
        defaultContractShouldNotBeFound("template.equals=" + UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllContractsByTemplateIsInShouldWork() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList where template in DEFAULT_TEMPLATE or UPDATED_TEMPLATE
        defaultContractShouldBeFound("template.in=" + DEFAULT_TEMPLATE + "," + UPDATED_TEMPLATE);

        // Get all the contractList where template equals to UPDATED_TEMPLATE
        defaultContractShouldNotBeFound("template.in=" + UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void getAllContractsByTemplateIsNullOrNotNull() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        // Get all the contractList where template is not null
        defaultContractShouldBeFound("template.specified=true");

        // Get all the contractList where template is null
        defaultContractShouldNotBeFound("template.specified=false");
    }

    @Test
    @Transactional
    public void getAllContractsByProjectIsEqualToSomething() throws Exception {
        // Initialize the database
        Project project = ProjectResourceIntTest.createEntity(em);
        em.persist(project);
        em.flush();
        contract.setProject(project);
        contractRepository.saveAndFlush(contract);
        Long projectId = project.getId();

        // Get all the contractList where project equals to projectId
        defaultContractShouldBeFound("projectId.equals=" + projectId);

        // Get all the contractList where project equals to projectId + 1
        defaultContractShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultContractShouldBeFound(String filter) throws Exception {
        restContractMockMvc.perform(get("/api/contracts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contract.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].expiresAt").value(hasItem(DEFAULT_EXPIRES_AT.toString())))
            .andExpect(jsonPath("$.[*].configuration").value(hasItem(DEFAULT_CONFIGURATION.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].template").value(hasItem(DEFAULT_TEMPLATE.booleanValue())));

        // Check, that the count call also returns 1
        restContractMockMvc.perform(get("/api/contracts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultContractShouldNotBeFound(String filter) throws Exception {
        restContractMockMvc.perform(get("/api/contracts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContractMockMvc.perform(get("/api/contracts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingContract() throws Exception {
        // Get the contract
        restContractMockMvc.perform(get("/api/contracts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContract() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        int databaseSizeBeforeUpdate = contractRepository.findAll().size();

        // Update the contract
        Contract updatedContract = contractRepository.findById(contract.getId()).get();
        // Disconnect from session so that the updates on updatedContract are not directly saved in db
        em.detach(updatedContract);
        updatedContract
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .subject(UPDATED_SUBJECT)
            .expiresAt(UPDATED_EXPIRES_AT)
            .configuration(UPDATED_CONFIGURATION)
            .content(UPDATED_CONTENT)
            .status(UPDATED_STATUS)
            .template(UPDATED_TEMPLATE);
        ContractDTO contractDTO = contractMapper.toDto(updatedContract);

        restContractMockMvc.perform(put("/api/contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractDTO)))
            .andExpect(status().isOk());

        // Validate the Contract in the database
        List<Contract> contractList = contractRepository.findAll();
        assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
        Contract testContract = contractList.get(contractList.size() - 1);
        assertThat(testContract.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContract.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testContract.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testContract.getExpiresAt()).isEqualTo(UPDATED_EXPIRES_AT);
        assertThat(testContract.getConfiguration()).isEqualTo(UPDATED_CONFIGURATION);
        assertThat(testContract.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testContract.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testContract.isTemplate()).isEqualTo(UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void updateNonExistingContract() throws Exception {
        int databaseSizeBeforeUpdate = contractRepository.findAll().size();

        // Create the Contract
        ContractDTO contractDTO = contractMapper.toDto(contract);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractMockMvc.perform(put("/api/contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contract in the database
        List<Contract> contractList = contractRepository.findAll();
        assertThat(contractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContract() throws Exception {
        // Initialize the database
        contractRepository.saveAndFlush(contract);

        int databaseSizeBeforeDelete = contractRepository.findAll().size();

        // Delete the contract
        restContractMockMvc.perform(delete("/api/contracts/{id}", contract.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Contract> contractList = contractRepository.findAll();
        assertThat(contractList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contract.class);
        Contract contract1 = new Contract();
        contract1.setId(1L);
        Contract contract2 = new Contract();
        contract2.setId(contract1.getId());
        assertThat(contract1).isEqualTo(contract2);
        contract2.setId(2L);
        assertThat(contract1).isNotEqualTo(contract2);
        contract1.setId(null);
        assertThat(contract1).isNotEqualTo(contract2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractDTO.class);
        ContractDTO contractDTO1 = new ContractDTO();
        contractDTO1.setId(1L);
        ContractDTO contractDTO2 = new ContractDTO();
        assertThat(contractDTO1).isNotEqualTo(contractDTO2);
        contractDTO2.setId(contractDTO1.getId());
        assertThat(contractDTO1).isEqualTo(contractDTO2);
        contractDTO2.setId(2L);
        assertThat(contractDTO1).isNotEqualTo(contractDTO2);
        contractDTO1.setId(null);
        assertThat(contractDTO1).isNotEqualTo(contractDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contractMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contractMapper.fromId(null)).isNull();
    }
}
