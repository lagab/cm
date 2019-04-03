package com.lagab.cmanager.web.rest;

import com.lagab.cmanager.CmApp;

import com.lagab.cmanager.domain.Dataset;
import com.lagab.cmanager.domain.Project;
import com.lagab.cmanager.repository.DatasetRepository;
import com.lagab.cmanager.service.DatasetService;
import com.lagab.cmanager.service.dto.DatasetDTO;
import com.lagab.cmanager.service.mapper.DatasetMapper;
import com.lagab.cmanager.web.rest.errors.ExceptionTranslator;
import com.lagab.cmanager.service.dto.DatasetCriteria;
import com.lagab.cmanager.service.DatasetQueryService;

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
import java.util.List;


import static com.lagab.cmanager.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DatasetResource REST controller.
 *
 * @see DatasetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmApp.class)
public class DatasetResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_HEADERS = "AAAAAAAAAA";
    private static final String UPDATED_HEADERS = "BBBBBBBBBB";

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private DatasetMapper datasetMapper;

    @Autowired
    private DatasetService datasetService;

    @Autowired
    private DatasetQueryService datasetQueryService;

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

    private MockMvc restDatasetMockMvc;

    private Dataset dataset;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DatasetResource datasetResource = new DatasetResource(datasetService, datasetQueryService);
        this.restDatasetMockMvc = MockMvcBuilders.standaloneSetup(datasetResource)
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
    public static Dataset createEntity(EntityManager em) {
        Dataset dataset = new Dataset()
            .name(DEFAULT_NAME)
            .key(DEFAULT_KEY)
            .headers(DEFAULT_HEADERS)
            .data(DEFAULT_DATA);
        return dataset;
    }

    @Before
    public void initTest() {
        dataset = createEntity(em);
    }

    @Test
    @Transactional
    public void createDataset() throws Exception {
        int databaseSizeBeforeCreate = datasetRepository.findAll().size();

        // Create the Dataset
        DatasetDTO datasetDTO = datasetMapper.toDto(dataset);
        restDatasetMockMvc.perform(post("/api/datasets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(datasetDTO)))
            .andExpect(status().isCreated());

        // Validate the Dataset in the database
        List<Dataset> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeCreate + 1);
        Dataset testDataset = datasetList.get(datasetList.size() - 1);
        assertThat(testDataset.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataset.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testDataset.getHeaders()).isEqualTo(DEFAULT_HEADERS);
        assertThat(testDataset.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createDatasetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = datasetRepository.findAll().size();

        // Create the Dataset with an existing ID
        dataset.setId(1L);
        DatasetDTO datasetDTO = datasetMapper.toDto(dataset);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatasetMockMvc.perform(post("/api/datasets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(datasetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dataset in the database
        List<Dataset> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetRepository.findAll().size();
        // set the field null
        dataset.setName(null);

        // Create the Dataset, which fails.
        DatasetDTO datasetDTO = datasetMapper.toDto(dataset);

        restDatasetMockMvc.perform(post("/api/datasets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(datasetDTO)))
            .andExpect(status().isBadRequest());

        List<Dataset> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetRepository.findAll().size();
        // set the field null
        dataset.setKey(null);

        // Create the Dataset, which fails.
        DatasetDTO datasetDTO = datasetMapper.toDto(dataset);

        restDatasetMockMvc.perform(post("/api/datasets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(datasetDTO)))
            .andExpect(status().isBadRequest());

        List<Dataset> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeadersIsRequired() throws Exception {
        int databaseSizeBeforeTest = datasetRepository.findAll().size();
        // set the field null
        dataset.setHeaders(null);

        // Create the Dataset, which fails.
        DatasetDTO datasetDTO = datasetMapper.toDto(dataset);

        restDatasetMockMvc.perform(post("/api/datasets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(datasetDTO)))
            .andExpect(status().isBadRequest());

        List<Dataset> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDatasets() throws Exception {
        // Initialize the database
        datasetRepository.saveAndFlush(dataset);

        // Get all the datasetList
        restDatasetMockMvc.perform(get("/api/datasets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataset.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].headers").value(hasItem(DEFAULT_HEADERS.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }
    
    @Test
    @Transactional
    public void getDataset() throws Exception {
        // Initialize the database
        datasetRepository.saveAndFlush(dataset);

        // Get the dataset
        restDatasetMockMvc.perform(get("/api/datasets/{id}", dataset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataset.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.headers").value(DEFAULT_HEADERS.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    @Transactional
    public void getAllDatasetsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        datasetRepository.saveAndFlush(dataset);

        // Get all the datasetList where name equals to DEFAULT_NAME
        defaultDatasetShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the datasetList where name equals to UPDATED_NAME
        defaultDatasetShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDatasetsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        datasetRepository.saveAndFlush(dataset);

        // Get all the datasetList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDatasetShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the datasetList where name equals to UPDATED_NAME
        defaultDatasetShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDatasetsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        datasetRepository.saveAndFlush(dataset);

        // Get all the datasetList where name is not null
        defaultDatasetShouldBeFound("name.specified=true");

        // Get all the datasetList where name is null
        defaultDatasetShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllDatasetsByKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        datasetRepository.saveAndFlush(dataset);

        // Get all the datasetList where key equals to DEFAULT_KEY
        defaultDatasetShouldBeFound("key.equals=" + DEFAULT_KEY);

        // Get all the datasetList where key equals to UPDATED_KEY
        defaultDatasetShouldNotBeFound("key.equals=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    public void getAllDatasetsByKeyIsInShouldWork() throws Exception {
        // Initialize the database
        datasetRepository.saveAndFlush(dataset);

        // Get all the datasetList where key in DEFAULT_KEY or UPDATED_KEY
        defaultDatasetShouldBeFound("key.in=" + DEFAULT_KEY + "," + UPDATED_KEY);

        // Get all the datasetList where key equals to UPDATED_KEY
        defaultDatasetShouldNotBeFound("key.in=" + UPDATED_KEY);
    }

    @Test
    @Transactional
    public void getAllDatasetsByKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        datasetRepository.saveAndFlush(dataset);

        // Get all the datasetList where key is not null
        defaultDatasetShouldBeFound("key.specified=true");

        // Get all the datasetList where key is null
        defaultDatasetShouldNotBeFound("key.specified=false");
    }

    @Test
    @Transactional
    public void getAllDatasetsByHeadersIsEqualToSomething() throws Exception {
        // Initialize the database
        datasetRepository.saveAndFlush(dataset);

        // Get all the datasetList where headers equals to DEFAULT_HEADERS
        defaultDatasetShouldBeFound("headers.equals=" + DEFAULT_HEADERS);

        // Get all the datasetList where headers equals to UPDATED_HEADERS
        defaultDatasetShouldNotBeFound("headers.equals=" + UPDATED_HEADERS);
    }

    @Test
    @Transactional
    public void getAllDatasetsByHeadersIsInShouldWork() throws Exception {
        // Initialize the database
        datasetRepository.saveAndFlush(dataset);

        // Get all the datasetList where headers in DEFAULT_HEADERS or UPDATED_HEADERS
        defaultDatasetShouldBeFound("headers.in=" + DEFAULT_HEADERS + "," + UPDATED_HEADERS);

        // Get all the datasetList where headers equals to UPDATED_HEADERS
        defaultDatasetShouldNotBeFound("headers.in=" + UPDATED_HEADERS);
    }

    @Test
    @Transactional
    public void getAllDatasetsByHeadersIsNullOrNotNull() throws Exception {
        // Initialize the database
        datasetRepository.saveAndFlush(dataset);

        // Get all the datasetList where headers is not null
        defaultDatasetShouldBeFound("headers.specified=true");

        // Get all the datasetList where headers is null
        defaultDatasetShouldNotBeFound("headers.specified=false");
    }

    @Test
    @Transactional
    public void getAllDatasetsByProjectIsEqualToSomething() throws Exception {
        // Initialize the database
        Project project = ProjectResourceIntTest.createEntity(em);
        em.persist(project);
        em.flush();
        dataset.setProject(project);
        datasetRepository.saveAndFlush(dataset);
        Long projectId = project.getId();

        // Get all the datasetList where project equals to projectId
        defaultDatasetShouldBeFound("projectId.equals=" + projectId);

        // Get all the datasetList where project equals to projectId + 1
        defaultDatasetShouldNotBeFound("projectId.equals=" + (projectId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDatasetShouldBeFound(String filter) throws Exception {
        restDatasetMockMvc.perform(get("/api/datasets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataset.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].headers").value(hasItem(DEFAULT_HEADERS)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));

        // Check, that the count call also returns 1
        restDatasetMockMvc.perform(get("/api/datasets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDatasetShouldNotBeFound(String filter) throws Exception {
        restDatasetMockMvc.perform(get("/api/datasets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDatasetMockMvc.perform(get("/api/datasets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDataset() throws Exception {
        // Get the dataset
        restDatasetMockMvc.perform(get("/api/datasets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDataset() throws Exception {
        // Initialize the database
        datasetRepository.saveAndFlush(dataset);

        int databaseSizeBeforeUpdate = datasetRepository.findAll().size();

        // Update the dataset
        Dataset updatedDataset = datasetRepository.findById(dataset.getId()).get();
        // Disconnect from session so that the updates on updatedDataset are not directly saved in db
        em.detach(updatedDataset);
        updatedDataset
            .name(UPDATED_NAME)
            .key(UPDATED_KEY)
            .headers(UPDATED_HEADERS)
            .data(UPDATED_DATA);
        DatasetDTO datasetDTO = datasetMapper.toDto(updatedDataset);

        restDatasetMockMvc.perform(put("/api/datasets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(datasetDTO)))
            .andExpect(status().isOk());

        // Validate the Dataset in the database
        List<Dataset> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeUpdate);
        Dataset testDataset = datasetList.get(datasetList.size() - 1);
        assertThat(testDataset.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataset.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testDataset.getHeaders()).isEqualTo(UPDATED_HEADERS);
        assertThat(testDataset.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingDataset() throws Exception {
        int databaseSizeBeforeUpdate = datasetRepository.findAll().size();

        // Create the Dataset
        DatasetDTO datasetDTO = datasetMapper.toDto(dataset);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatasetMockMvc.perform(put("/api/datasets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(datasetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dataset in the database
        List<Dataset> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDataset() throws Exception {
        // Initialize the database
        datasetRepository.saveAndFlush(dataset);

        int databaseSizeBeforeDelete = datasetRepository.findAll().size();

        // Delete the dataset
        restDatasetMockMvc.perform(delete("/api/datasets/{id}", dataset.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dataset> datasetList = datasetRepository.findAll();
        assertThat(datasetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dataset.class);
        Dataset dataset1 = new Dataset();
        dataset1.setId(1L);
        Dataset dataset2 = new Dataset();
        dataset2.setId(dataset1.getId());
        assertThat(dataset1).isEqualTo(dataset2);
        dataset2.setId(2L);
        assertThat(dataset1).isNotEqualTo(dataset2);
        dataset1.setId(null);
        assertThat(dataset1).isNotEqualTo(dataset2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DatasetDTO.class);
        DatasetDTO datasetDTO1 = new DatasetDTO();
        datasetDTO1.setId(1L);
        DatasetDTO datasetDTO2 = new DatasetDTO();
        assertThat(datasetDTO1).isNotEqualTo(datasetDTO2);
        datasetDTO2.setId(datasetDTO1.getId());
        assertThat(datasetDTO1).isEqualTo(datasetDTO2);
        datasetDTO2.setId(2L);
        assertThat(datasetDTO1).isNotEqualTo(datasetDTO2);
        datasetDTO1.setId(null);
        assertThat(datasetDTO1).isNotEqualTo(datasetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(datasetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(datasetMapper.fromId(null)).isNull();
    }
}
