package com.lagab.cmanager.web.rest;

import com.lagab.cmanager.CmApp;

import com.lagab.cmanager.domain.Workspace;
import com.lagab.cmanager.repository.WorkspaceRepository;
import com.lagab.cmanager.service.WorkspaceService;
import com.lagab.cmanager.service.dto.WorkspaceDTO;
import com.lagab.cmanager.service.mapper.WorkspaceMapper;
import com.lagab.cmanager.web.rest.errors.ExceptionTranslator;

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
import java.util.List;


import static com.lagab.cmanager.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lagab.cmanager.domain.enumeration.Visibility;
/**
 * Test class for the WorkspaceResource REST controller.
 *
 * @see WorkspaceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmApp.class)
public class WorkspaceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Visibility DEFAULT_VISIBILITY = Visibility.PRIVATE;
    private static final Visibility UPDATED_VISIBILITY = Visibility.PROTECTED;

    private static final String DEFAULT_PATH = "path";
    private static final String UPDATED_PATH = "x";

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private WorkspaceMapper workspaceMapper;

    @Autowired
    private WorkspaceService workspaceService;

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

    private MockMvc restWorkspaceMockMvc;

    private Workspace workspace;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkspaceResource workspaceResource = new WorkspaceResource(workspaceService);
        this.restWorkspaceMockMvc = MockMvcBuilders.standaloneSetup(workspaceResource)
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
    public static Workspace createEntity(EntityManager em) {
        Workspace workspace = new Workspace()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .visibility(DEFAULT_VISIBILITY)
            .path(DEFAULT_PATH);
        return workspace;
    }
    /**
     * Create an  other entity a test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Workspace createNewEntity(EntityManager em) {
        Workspace workspace = new Workspace()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .visibility(DEFAULT_VISIBILITY)
            .path(UPDATED_PATH);
        return workspace;
    }

    @Before
    public void initTest() {
        workspace = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkspace() throws Exception {
        int databaseSizeBeforeCreate = workspaceRepository.findAll().size();

        // Create the Workspace
        WorkspaceDTO workspaceDTO = workspaceMapper.toDto(workspace);
        restWorkspaceMockMvc.perform(post("/api/workspaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workspaceDTO)))
            .andExpect(status().isCreated());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeCreate + 1);
        Workspace testWorkspace = workspaceList.get(workspaceList.size() - 1);
        assertThat(testWorkspace.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkspace.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWorkspace.getVisibility()).isEqualTo(DEFAULT_VISIBILITY);
        assertThat(testWorkspace.getPath()).isEqualTo(DEFAULT_PATH);
    }

    @Test
    @Transactional
    public void createWorkspaceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workspaceRepository.findAll().size();

        // Create the Workspace with an existing ID
        workspace.setId(1L);
        WorkspaceDTO workspaceDTO = workspaceMapper.toDto(workspace);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkspaceMockMvc.perform(post("/api/workspaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workspaceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = workspaceRepository.findAll().size();
        // set the field null
        workspace.setName(null);

        // Create the Workspace, which fails.
        WorkspaceDTO workspaceDTO = workspaceMapper.toDto(workspace);

        restWorkspaceMockMvc.perform(post("/api/workspaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workspaceDTO)))
            .andExpect(status().isBadRequest());

        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVisibilityIsRequired() throws Exception {
        int databaseSizeBeforeTest = workspaceRepository.findAll().size();
        // set the field null
        workspace.setVisibility(null);

        // Create the Workspace, which fails.
        WorkspaceDTO workspaceDTO = workspaceMapper.toDto(workspace);

        restWorkspaceMockMvc.perform(post("/api/workspaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workspaceDTO)))
            .andExpect(status().isBadRequest());

        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPathIsRequired() throws Exception {
        int databaseSizeBeforeTest = workspaceRepository.findAll().size();
        // set the field null
        workspace.setPath(null);

        // Create the Workspace, which fails.
        WorkspaceDTO workspaceDTO = workspaceMapper.toDto(workspace);

        restWorkspaceMockMvc.perform(post("/api/workspaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workspaceDTO)))
            .andExpect(status().isBadRequest());

        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkspaces() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        // Get all the workspaceList
        restWorkspaceMockMvc.perform(get("/api/workspaces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workspace.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].visibility").value(hasItem(DEFAULT_VISIBILITY.toString())))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())));
    }

    @Test
    @Transactional
    public void getWorkspace() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        // Get the workspace
        restWorkspaceMockMvc.perform(get("/api/workspaces/{id}", workspace.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workspace.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.visibility").value(DEFAULT_VISIBILITY.toString()))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkspace() throws Exception {
        // Get the workspace
        restWorkspaceMockMvc.perform(get("/api/workspaces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkspace() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        int databaseSizeBeforeUpdate = workspaceRepository.findAll().size();

        // Update the workspace
        Workspace updatedWorkspace = workspaceRepository.findById(workspace.getId()).get();
        // Disconnect from session so that the updates on updatedWorkspace are not directly saved in db
        em.detach(updatedWorkspace);
        updatedWorkspace
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .visibility(UPDATED_VISIBILITY)
            .path(UPDATED_PATH);
        WorkspaceDTO workspaceDTO = workspaceMapper.toDto(updatedWorkspace);

        restWorkspaceMockMvc.perform(put("/api/workspaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workspaceDTO)))
            .andExpect(status().isOk());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeUpdate);
        Workspace testWorkspace = workspaceList.get(workspaceList.size() - 1);
        assertThat(testWorkspace.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkspace.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWorkspace.getVisibility()).isEqualTo(UPDATED_VISIBILITY);
        assertThat(testWorkspace.getPath()).isEqualTo(UPDATED_PATH);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkspace() throws Exception {
        int databaseSizeBeforeUpdate = workspaceRepository.findAll().size();

        // Create the Workspace
        WorkspaceDTO workspaceDTO = workspaceMapper.toDto(workspace);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkspaceMockMvc.perform(put("/api/workspaces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workspaceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Workspace in the database
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkspace() throws Exception {
        // Initialize the database
        workspaceRepository.saveAndFlush(workspace);

        int databaseSizeBeforeDelete = workspaceRepository.findAll().size();

        // Delete the workspace
        restWorkspaceMockMvc.perform(delete("/api/workspaces/{id}", workspace.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Workspace> workspaceList = workspaceRepository.findAll();
        assertThat(workspaceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Workspace.class);
        Workspace workspace1 = new Workspace();
        workspace1.setId(1L);
        Workspace workspace2 = new Workspace();
        workspace2.setId(workspace1.getId());
        assertThat(workspace1).isEqualTo(workspace2);
        workspace2.setId(2L);
        assertThat(workspace1).isNotEqualTo(workspace2);
        workspace1.setId(null);
        assertThat(workspace1).isNotEqualTo(workspace2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkspaceDTO.class);
        WorkspaceDTO workspaceDTO1 = new WorkspaceDTO();
        workspaceDTO1.setId(1L);
        WorkspaceDTO workspaceDTO2 = new WorkspaceDTO();
        assertThat(workspaceDTO1).isNotEqualTo(workspaceDTO2);
        workspaceDTO2.setId(workspaceDTO1.getId());
        assertThat(workspaceDTO1).isEqualTo(workspaceDTO2);
        workspaceDTO2.setId(2L);
        assertThat(workspaceDTO1).isNotEqualTo(workspaceDTO2);
        workspaceDTO1.setId(null);
        assertThat(workspaceDTO1).isNotEqualTo(workspaceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(workspaceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(workspaceMapper.fromId(null)).isNull();
    }
}
